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
	
	public TCPServerThread(Node n) throws IOException {
		node = n; 
	}
	
	public void run() {
		try {
			serverSocket = new ServerSocket(node.getPortNumber()); //FIX: Close this?? 
			node.setPortNumber(serverSocket.getLocalPort());
			
			while (true) {
				
				Socket socket = serverSocket.accept(); 
				new Connection(socket, node);
			}
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
	

}
