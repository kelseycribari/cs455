package cs455.overlay.transport;

import java.io.IOException;
import java.net.Socket;

import cs455.overlay.node.Node;



public class Connection {
	
	private String name; 
	private String IP; 
	private String localIP; 
	private int port; 
	private int localPort;
	private int listeningPort; 
	
	private int linkWeight; 
	
	//the owner of this connection referenced by n
	private Node n; 
	
	
	//link the network 
	private Socket s; 
	private TCPReceiverThread tcpreceiver; 
	private TCPSender tcpsender; 
	
	//private int localPort; //new node connects with this n using this port
	//private int inetPort; //remote node connection for new node 
	//private String ID; 
	
	
	public Connection(Socket socket, Node node) throws IOException {
		System.out.println("Creating new connection");
		IP = socket.getInetAddress().getHostName(); 
		localIP = socket.getLocalAddress().getHostName(); 
		port = socket.getPort(); 
		localPort = socket.getLocalPort(); 
		listeningPort = 0; 
		name = IP + ":" + port; 
		s = socket; 
		n = node; 
		
		tcpreceiver = new TCPReceiverThread(socket, node);
		tcpreceiver.start(); 
		tcpsender = new TCPSender(socket);
		//getInetAddress returns the IP Address of the remote machine that you are connecting to 
		//getInetAddress.getHostAddress returns the String representation of that IP Address 
		node.registerConnection(this);
		
	}
	
	
	public boolean sendData (byte[] data) {
		try {
			tcpsender.sendData(data);
		} catch (IOException e) {
			e.printStackTrace(); 
		}
		return true; 
	}
	
	//getters and setters 
	public String getName() {
		return name; 
	}

	public Node getNode() {
		return n; 
	}
	
	public String getIP() {
		return IP; 
	}
	
	public String getLocalIP() {
		return localIP; 
	}
	
	public int getPort() {
		return port; 
	}
	
	public int getLocalPort() {
		return localPort; 
	}
	
	public int getListeningPort() {
		return listeningPort; 
	}
	
	public void setListeningPort(int p) {
		listeningPort = p; 
	}
	
	public void setLinkWeight(int weight) {
		linkWeight = weight; 
	}
	
	public int getLinkWeight() {
		return linkWeight; 
	}
}
