package cs455.overlay.node;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;

import cs455.overlay.transport.Connection;
import cs455.overlay.transport.ConnectionsHandler;
import cs455.overlay.transport.TCPServerThread;
import cs455.overlay.wireformats.Event;
import cs455.overlay.wireformats.EventFactory;
import cs455.overlay.wireformats.Protocol;
import cs455.overlay.wireformats.Register;
import cs455.overlay.wireformats.RegistrationResponse;

public class Registry implements Node {

	//manages server connections on a thread
	//private ConnectionsHandler connectionsHandler;
	TCPServerThread serverThread; 
	
	private InetAddress locAddr; 
	private int port; 
	
	private int messagingNodeConnections; //how many other nodes each node is connected to. 
	
	EventFactory eFactory; 
	
	private int completedTasks; 
	
	
	//data structure to store all incoming connections
	private Hashtable<String, Connection> messagingNodes; 
	//manages all messaging nodes
	//inserted after being a registered connection
	//private LinkedHashMap<String, Connection> messagingNodes; 
	
	public Registry(int port) throws IOException {
		serverThread = new TCPServerThread(this);
		serverThread.start(); 
		messagingNodes = new Hashtable<String, Connection>(); 
		completedTasks = 0; 
		this.port = port; 
		
		//set local address 
	}
	
	/*public void start() throws IOException {
		
		//eFactory = EventFactory.getInstance(); 
		connectionsHandler = new ConnectionsHandler(this, port);
		System.out.println("Registry start()"); 
		connectionsHandler.start(); 
		
		connections = new Hashtable<String, Connection>(); 
		//messagingNodes = new LinkedHashMap<String, Connection>(); 
	}
	*/
	
	@Override
	public synchronized void onEvent(Event event, Socket socket) {
		
		switch(event.getEventType()) {
		
		case Protocol.REGISTER_REQUEST: {
				Register rr = (Register) event;
				//need to validate registration 
		}
		
		//working here on Deregistering 
		
		
		}
		
	}
	
	public byte validRegister(Register rr, Socket socket) throws IOException {
		//success = 0, failure != 0
		byte success; 
		
		String information;
		
		Connection connection = messagingNodes.get(socket.getInetAddress().getCanonicalHostName() + ":" + socket.getPort());
		connection.setListeningPort(rr.getPort()); //need to add a get port for the register class 
		
		
		if (rr.getIP().equals(socket.getInetAddress().getCanonicalHostName())) { //also need a getIP for the register class
			success = 0; 
			information = "Successful registration request. There are currently " + 
							messagingNodes.size() + " messaging nodes in the overlay.";
		}
		else {
			success = -1; 
			deregisterConnection(connection);
			information = "Unsuccessful registration request. The IP Address of the request: " + 
							rr.getIP() + " does not match the IP Address of the source: " + socket.getInetAddress().toString();
		}
		
		RegistrationResponse response = new RegistrationResponse(); 
		response.setSuccess(success); 
		response.additionalInfo(information);
		
		connection.sendData(response.getBytes());
		
		return success; 
		
		
	}
	//need to validate deregistering!
	
	public int getPortNumber() {
		return port; 
	}
	
	public void setPortNumber(int portNum) {
		port = portNum; 
	}
	
	

	//registers new connection with list of connections, not with messagingNodes 
	@Override
	public void registerConnection(Connection c) {
		System.out.println("Registering connection on: " + c.getName());
		
		if (!(messagingNodes.containsKey(c.getName()))) {
			messagingNodes.put(c.getName(), c);
		}
		else 
			System.out.println("Connection already exists.");
		
	}

	
	//removes connection from list of all connections not from messaging nodes 
	@Override
	public void deregisterConnection(Connection c) {
		System.out.println("Deregistering connection on: " + c.getName());
		messagingNodes.remove(c.getName());
		
	}
	
	
public static void main (String [] args) throws IOException {
		
		if (args.length == 0) {
			System.out.println("ERROR: UNSPECIFIED PORT NUMBER");
			return;
		}
		
		//parse the number to an int 
		int portNumber = Integer.parseInt(args[0]);
		 
		
		Registry registry = null; 
		System.out.println("Port Number: " + portNumber);
		
		try {
			registry = new Registry(portNumber);
			//registry.start(); 
		} catch (IOException e){
			System.out.println("ERROR: COULD NOT START REGISTRY");
			System.exit(0);
		}
		
		
		
	}

}
