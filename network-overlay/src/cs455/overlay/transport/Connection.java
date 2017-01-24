package cs455.overlay.transport;

import java.io.IOException;
import java.net.Socket;

import cs455.overlay.node.Node;



public class Connection {
	
	protected Node n; 
	protected Socket s; 
	
	protected TCPReceiverThread tcpreceiver; 
	protected TCPSender tcpsend; 
	
	
	public Connection(Socket socket, Node node) throws IOException {
		s = socket; 
		n = node; 
		
		tcpreceiver = new TCPReceiverThread(socket, node);
		tcpreceiver.start(); 
		tcpsend = new TCPSender(socket);
	}

}
