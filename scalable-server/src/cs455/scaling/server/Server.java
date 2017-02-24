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
import java.util.Iterator;

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
	
	
	public Server(int port, int numberThreads) {
		this.port = port; 
		manager = new ThreadPoolManager(numberThreads); 
		
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
		serverSocketChannel.register(socketSelector, SelectionKey.OP_ACCEPT, new ChannelUtil()); //ChannelUtil to manage the channel? 
		
		manager.initialize(); 
	}
	
	public void begin() {
		while(!Thread.interrupted()) {
			int selectedKeys = 0; 
			
			try {
				selectedKeys = this.socketSelector.select(); 
				
				if (selectedKeys == 0) {
					continue; 
				}
				
				Iterator selected = this.socketSelector.selectedKeys().iterator(); 
				while(selected.hasNext()) {
					SelectionKey key = (SelectionKey) selected.next(); 
					selected.remove(); 
					
					if (!key.isValid()) {
						continue; 
					}
					
					if (key.isAcceptable()) {
						accept(key); 
					}
					
					if (key.isReadable()) {
						read(key); 
					}
					
					if (key.isWritable()) {
						write(key); 
					}
				}
				
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * Allows accepting of connections by on the given selection key's channel. 
	 */
	
	private void accept(SelectionKey selectionKey) throws IOException {
		
		//in order for the accept to be pending, the channel needs to be a server socket channel, so cast the selection key's channel to server socket channel
		ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel(); 
		
		//accepts the connection and makes it non-blocking 
		SocketChannel socketChannel = serverSocketChannel.accept(); 
		Socket socket = socketChannel.socket(); 
		socketChannel.configureBlocking(false); 
		
		//maybe add a channelUtil to the registration? 
		socketChannel.register(this.socketSelector, selectionKey.OP_READ, new ChannelUtil());
		
		
		//TODO Add print statement to let user know what's happening 
		
	}
	
	/*
	 * Read method reads data from the channel that is associated with the give selection key. 
	 * Reads in the PACKET_SIZE worth of data (8kb) and calcultes the checksum and adds it to the 
	 * list of pending data that's attached to the key 
	 */
	
	private void read(SelectionKey selectionKey) {
		ChannelUtil state = (ChannelUtil) selectionKey.attachment(); 
		ReadTask readTask = new ReadTask(selectionKey, PACKET_SIZE); 
		if (!state.isReading()) {
			manager.queueTask(readTask);
		}
	}
	
	/*
	 * Write method writes pending data that is attached to the selectionKey
	 */
	
	private void write(SelectionKey selectionKey) {
		ChannelUtil state = (ChannelUtil) selectionKey.attachment(); 
		WriteTask writeTask = new WriteTask(selectionKey);
		if (!state.isWriting()) {
			manager.queueTask(writeTask);
		}
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
		
		Server server = null; 
		
		try {
			server = new Server(port, threadPoolSize);
			server.initSelector();
			server.begin(); 
		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}
	
	

}
