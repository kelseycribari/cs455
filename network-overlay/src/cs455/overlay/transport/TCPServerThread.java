package cs455.overlay.transport;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import cs455.overlay.node.Node;

public class TCPServerThread extends Thread {


	//host listening on behalf of 
	private Node node; 

	//main socket
	private ServerSocket serverSocket; 

	int localPort; 

	public TCPServerThread(Node n) throws IOException {
		node = n; 

		System.out.println("InsideTCPServerThread's constructor");
		try {
			//starts on random port if not given port number
			serverSocket = new ServerSocket(0); 
			localPort = serverSocket.getLocalPort(); 
			
		} catch (IOException e) {
			throw e; 
		}
	}

	public TCPServerThread(Node n, int port) throws IOException {
		node = n; 
		localPort = port; 
		System.out.println("TCPServerThread local port: " + localPort);
		try {
			serverSocket = new ServerSocket(localPort); 

		} catch (IOException e) {
			throw e; 
		}
	}

	public void run() {

		
		while (true) {
			
			try {
				//sets up a socket using the serversocket 
				System.out.println("TCPServerThread's run method");
				Socket socket = serverSocket.accept(); 
				System.out.println("Inside TCPServerThread's try block");
				Connection c = new Connection(socket, node); 
				System.out.println("TCPServerThread: serversocket now accepting connections.");
			} catch (IOException e) {
				e.printStackTrace(); 
				continue; //continues to the next connection if one fails--fixes issue of not being able to reuse the port right away 
			}
			
			//Socket socket = serverSocket.accept(); 
			//new Connection(socket, node);
		}
	}
	
	


	public int getLocalPort() {
		return localPort; 
	}

}
