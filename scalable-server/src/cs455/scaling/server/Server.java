package cs455.scaling.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import cs455.scaling.client.Client;
import cs455.scaling.task.ReadTask;
import cs455.scaling.task.WriteTask;
import cs455.scaling.threadpool.ThreadPoolManager;
import cs455.scaling.util.ChannelUtil;

/*
 * @Author: Kelsey Cribari 
 * Date: 2-21-17
 * Server class manages connections with multiple clients all at the same time. 
 */

public class Server {

	private static final int PACKET_SIZE = 8192; //8kb? 

	private int port; 
	Selector socketSelector; 
	ThreadPoolManager manager; 
	int clientCounter; 
	ArrayList<Client> clientConnections; 
	int messageThroughput; 
	ServerStatus serverStatus; 
	private volatile boolean running = true; 
	private volatile int count = 0; 

	public Server(int port, int numberThreads) {
		this.port = port; 
		manager = new ThreadPoolManager(numberThreads); 
		clientCounter = 0; 
		messageThroughput = 0; 
		serverStatus = new ServerStatus(); 

	}

	public void initSelector() throws IOException {

		//opens the socket selector
		this.socketSelector = Selector.open(); 

		//Creates a new non-blocking server socket channel 
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open(); 
		serverSocketChannel.configureBlocking(false); 

		//binds the server socket channel to the specified port 
		InetSocketAddress inetSocketAddress = new InetSocketAddress(this.port); 
		serverSocketChannel.bind(inetSocketAddress); 

		//registers the server socket channel and indicates that it is interested in accepting new connections 
		serverSocketChannel.register(socketSelector, SelectionKey.OP_ACCEPT); //ChannelUtil to manage the channel? CHANGED THIS TO REMOVE CHANNEL UTIL

		//System.out.println(serverSocketChannel + " " + socketSelector);

		manager.initialize(); 
	}

	public void start() {
		long startTime = System.nanoTime(); 
		while(!Thread.interrupted()) {
			int selectedKeys = 0; 
			//int count = 0; 
			//int messageThroughput = 0; 
			try {
				//sees if anything is ready 
				selectedKeys = this.socketSelector.select();
				//System.out.println(selectedKeys);
			} catch(IOException e) {
				e.printStackTrace();
			}

			//if nothing was ready, then go back and wait again 
			if (selectedKeys == 0) {
				continue; 
			}
			//System.out.println("SERVER: while loop inside start.");
			//Iterator selected = this.socketSelector.selectedKeys().iterator(); 
			//			Timer timer = new Timer(); 
			//			timer.schedule(new TimerTask(){
			//				@Override
			//				public void run() {
			//					printThroughputClientConnections();
			//				}
			//			}, 0, 5000);
			//System.out.println("SERVER COUNT: " + count);


			Iterator<SelectionKey> selected = this.socketSelector.selectedKeys().iterator(); 
			while(selected.hasNext()) {
				SelectionKey key = (SelectionKey)selected.next(); //Cast to SelectionKey? 
				selected.remove(); 

				if (!key.isValid()) {
					continue; 
				}

				if (key.isAcceptable()) {
					try {
						accept(key);
					} catch (IOException e) {
						e.printStackTrace();
					} 
				}

				if (key.isReadable()) {
					read(key); 
				}

				if (key.isWritable()) {
					write(key); 
				}


				//messageThroughput++; 
				//printThroughputClientConnections(); 
				long elapsedTime = System.nanoTime() - startTime;  
				elapsedTime = (long) (elapsedTime / 1000000000.0);
				count++; 
				//System.out.println(elapsedTime);
				if (elapsedTime == 5) {
					startTime = System.nanoTime();
					count = 0; 
					String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
					//runs for 5 seconds, so divide by 5, and adds both read and write of messages so need to divide by 2 to get number processed
					long packetsSent = (serverStatus.getPacketsSent() / 5) / 2; 
					System.out.println("[" + timeStamp + "]" + " Current Server Throughput: " + packetsSent + "/second" + 
							", Active Client Connections: " + serverStatus.getConnectedClients());
				}
			}

			





		}
	}

	/*
	 * Allows accepting of connections by on the given selection key's channel. 
	 */

	private void accept(SelectionKey selectionKey) throws IOException {
		//System.out.println("SERVER: accept");

		//in order for the accept to be pending, the channel needs to be a server socket channel, so cast the selection key's channel to server socket channel
		ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel(); 

		//accepts the connection and makes it non-blocking 
		SocketChannel socketChannel = serverSocketChannel.accept(); 
		//Socket socket = socketChannel.socket(); 
		socketChannel.configureBlocking(false); 

		serverStatus.addClient(); 

		//maybe add a channelUtil to the registration? 
		socketChannel.register(this.socketSelector, selectionKey.OP_READ, new ChannelUtil());


		//TODO Add print statement to let user know what's happening 
		System.out.println("Connection from " + socketChannel.socket().getInetAddress().getHostName() + ":" + socketChannel.socket().getPort() + " has been accepted.");
		clientCounter++; 
	}

	/*
	 * Read method reads data from the channel that is associated with the give selection key. 
	 * Reads in the PACKET_SIZE worth of data (8kb) and calcultes the checksum and adds it to the 
	 * list of pending data that's attached to the key 
	 */

	private void read(SelectionKey selectionKey) {
		//System.out.println("SERVER: read");
		ChannelUtil state = (ChannelUtil) selectionKey.attachment(); 
		//ReadTask readTask = new ReadTask(selectionKey, PACKET_SIZE); 
		if (!state.isReading()) {
			manager.queueTask(new ReadTask(selectionKey, PACKET_SIZE, serverStatus));
		}
	}

	/*
	 * Write method writes pending data that is attached to the selectionKey
	 */

	private void write(SelectionKey selectionKey) {
		//System.out.println("SERVER: write");
		ChannelUtil state = (ChannelUtil) selectionKey.attachment(); 
		//WriteTask writeTask = new WriteTask(selectionKey);
		if (!state.isWriting()) {
			manager.queueTask(new WriteTask(selectionKey, serverStatus));
		}
	}

	private void printThroughputClientConnections() {
		long currentTime = System.nanoTime(); 
		//		if (clientCounter == 0) {
		//			System.exit(0);
		//		}

		messageThroughput = 0;

		//while(true) {


		//}
	}

	public void stop() {
		running = false; 
		Thread.currentThread().interrupt(); 
	}


	public static void main(String[] args) {
		int port = 0; 
		int threadPoolSize = 0; 

		if (args.length != 2) {
			System.err.println("SERVER ERROR: Invalid arguments. Please input a portnumber and a thread pool size.");
			System.exit(0);
		}
		else {
			port = Integer.parseInt(args[0]);
			threadPoolSize = Integer.parseInt(args[1]);
		}

		try {
			System.out.println("Server started on: " + InetAddress.getLocalHost().getHostName() + " using port: " + port); 

		} catch (UnknownHostException uhe) {
			uhe.printStackTrace();
		}

		Server server = new Server(port, threadPoolSize); 

		try {
			//server = new Server(port, threadPoolSize);
			server.initSelector();
			Runtime.getRuntime().addShutdownHook(new EndThread(server));

		} catch (IOException ie) {
			System.err.println("SERVER ERROR: Server not initialized. Exiting..."); 
			System.exit(0);
		}

		server.start();



	}

	public static class EndThread extends Thread {
		private final Server server; 
		public EndThread(Server server) {
			this.server = server; 
		}
		@Override 
		public void run() {
			server.stop(); 
		}
	}



}
