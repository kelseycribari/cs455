package cs455.scaling.client;

import java.io.IOException;
import java.net.UnknownHostException; 
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
//import java.rmi.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
//import java.util.concurrent.ThreadLocalRandom;


/*
 * Author: Kelsey Cribari 
 * Allow for at least 100 clients created at a time.
 * The goal of a client is to maintain an active connection to the server, regularly send data packets of 8KB to the server.
 * It also tracks the hashcodes of the data packets that it sends to the server. 
 * It can also receive the hashcodes from the server to indicate that the packets have been received. (done in the ResponseHandler thread)
 */

public class Client {
	
	private static final int PACKET_SIZE = 8192;
	private SocketChannel socketChannel; 
	
	//private InetAddress serverHost;
	//private String serverHost; 
	//private int serverPort; 
	public int messageRate; 
	
	private LinkedList<String> pendingHashes; 
	private Selector selector; 
	private SelectionKey selectionKey; 
	
	private ResponseHandler responseHandler; 
	
	
	
	public Client(int messageRate) throws IOException {
		socketChannel = SocketChannel.open(); 
		responseHandler = new ResponseHandler(socketChannel); 
		//this.serverHost = serverHost; 
		//this.serverPort = serverPort; 
		this.messageRate = messageRate; 
		//pendingHashes = new LinkedList<String>(); 
		//responseHandler = new ResponseHandler(socketChannel);
		//selector = Selector.open(); 
		//this.initiateConnection(); 
		//socketChannel.configureBlocking(false); 
		
	}
	

//	public void send() {
//		while(true) {
//			try {
//				selector.select(); 
//				
//				Iterator<SelectionKey> selectionKeys = selector.selectedKeys().iterator(); 
//				while(selectionKeys.hasNext()) {
//					SelectionKey nextKey = selectionKeys.next(); 
//					selectionKeys.remove(); 
//					
//					
//					if (!nextKey.isValid()) {
//						continue; 
//					}
//					
//					if (nextKey.isConnectable()) {
//						finishConnection(nextKey); 
//						continue; 
//					}
//					if (nextKey.isReadable()) {
//						read(nextKey); 
//						continue; 
//					}
//					if (nextKey.isWritable()) {
//						write(nextKey); 
//						continue; 
//					}
//				}
//			} catch(IOException e) {
//				e.printStackTrace();
//			}
//		}
//		
//	}
	
	public void initiateConnection(String serverIP, int serverPort) throws IOException {
		System.out.println("Connecting client to server.");
		
		//socketChannel = SocketChannel.open(); 
		//socketChannel.configureBlocking(false); 
		
		
		
		//InetSocketAddress isa = new InetSocketAddress(serverIP, serverPort); 
		socketChannel.connect(new InetSocketAddress(serverIP, serverPort));
		//socketChannel.register(selector, SelectionKey.OP_CONNECT);
		//System.out.println(socketChannel.isConnected());
		//responseHandler = new ResponseHandler(socketChannel);
		System.out.println("Client conencted to: " + serverIP + " on port: " + serverPort);
		//System.out.println(socketChannel.toString());
		new Thread(responseHandler).start(); 
	}
	
	private void finishConnection(SelectionKey key) {
		SocketChannel channel = (SocketChannel) key.channel(); 
		
		try {
			channel.finishConnect(); 
			System.out.println("Connection finished.");
		} catch(IOException ioe) {
			key.channel(); 
			return; 
		}
		
		//set the interest to write 
		key.interestOps(SelectionKey.OP_WRITE);
	}
	
//	private void write(SelectionKey selectionKey) throws IOException {
//		
//		try {
//			Thread.sleep(1000 / messageRate);	
//		} catch(InterruptedException ie) {
//			ie.printStackTrace();
//			return; 
//		}
//		
//		byte[] data = new byte[PACKET_SIZE];
//		ThreadLocalRandom.current().nextBytes(data);
//		String hash = null; 
//		
//		try {
//			hash = SHA1FromBytes(data); 
//			
//		} catch(NoSuchAlgorithmException e) {
//			e.printStackTrace();
//			return; 
//		}
//		
//		System.out.println("Adding hash: " + hash + " to pending hashes.");
//		pendingHashes.add(hash); 
//		
//		SocketChannel channel = (SocketChannel) selectionKey.channel();  
//		
//		ByteBuffer byteBuffer = ByteBuffer.wrap(data); 
//		channel.write(byteBuffer); 
//		
//		selectionKey.interestOps(SelectionKey.OP_READ);
//	}
 	
//	private void read(SelectionKey selectionKey) throws IOException {
//		SocketChannel channel = (SocketChannel) selectionKey.channel(); 
//		ByteBuffer byteBuffer = ByteBuffer.allocate(PACKET_SIZE); 
//		
//		
//	}
	
	
	public void start() throws NoSuchAlgorithmException {
		System.out.println("Client started sending messages at a rate of " + messageRate + " per second.");
		
		while(!Thread.interrupted()) {
			try {
				send(); 
			} catch (IOException e) {
				System.out.println("Server disconnected, giving up.");
				return; 
			}
			
			try {
				Thread.sleep(1000 / messageRate);
			} catch(InterruptedException e) {
				return; 
			}
		}
	}
	
	
	private void send() throws IOException, NoSuchAlgorithmException {
		
		byte[] packet = new byte[PACKET_SIZE];
		
		new Random().nextBytes(packet); 
		//ThreadLocalRandom.current().nextBytes(packet);
		//String hash = null; 
		
		MessageDigest digest = null; 
		try {
			digest = MessageDigest.getInstance("SHA1");
		} catch (NoSuchAlgorithmException nsae) {
			nsae.printStackTrace(); 
		}
		
		byte[] hash = digest.digest(packet);
		responseHandler.addPending(hash);
		
//		try {
//			hash = SHA1FromBytes(packet); 
//			System.out.println(responseHandler.toString());
//			responseHandler.addPending(hash);
//		} catch(NoSuchAlgorithmException nsae) {
//			nsae.printStackTrace();
//		}
		
		ByteBuffer byteBuffer = ByteBuffer.allocate(PACKET_SIZE);
		byteBuffer.put(packet);
		byteBuffer.flip(); 
		
		while(byteBuffer.hasRemaining()) {
			socketChannel.write(byteBuffer);
		}
		BigInteger printHashInteger = new BigInteger(1, hash);
		String printHashString = printHashInteger.toString(16); 
		System.out.println("Sent " + printHashString + " to the Server.");
		
		
		
	}
	
	private String SHA1FromBytes(byte[] data) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA1");
		byte[] hash = digest.digest(data); 
		BigInteger hashInt = new BigInteger(1, hash); 
		
		return hashInt.toString(16); 
	}
	
	
	public static void main (String args[]) throws UnknownHostException, NoSuchAlgorithmException {
		
		String hostName = null; 
		int serverPort = 0; 
		int msgRate = 0; 
		
		//Check that there are three total arguments 
		if (args.length != 3) {
			System.err.println("CLIENT ERROR: Invalid number of arguments. Please provide: server-host server-port and message-rate.");
			System.exit(0);
		}
		else {
			//try {
				//hostName = InetAddress.getByName(args[0]);
				hostName = args[0];
			//} catch (UnknownHostException e) {
				//e.printStackTrace();
			//}
			serverPort = Integer.parseInt(args[1]);
			msgRate = Integer.parseInt(args[2]);
			
		}
		Client client = null; 
		try {
			client = new Client(msgRate);
			client.initiateConnection(hostName, serverPort);
		} catch (IOException e) {
			e.printStackTrace(); 
		}
		
		client.start(); 
		
		
		
	}



	

}
