package cs455.overlay.node;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

import cs455.overlay.transport.Connection;
import cs455.overlay.transport.ConnectionsHandler;
import cs455.overlay.transport.TCPServerThread;
import cs455.overlay.wireformats.Deregister;
import cs455.overlay.wireformats.DeregistrationResponse;
import cs455.overlay.wireformats.Event;
import cs455.overlay.wireformats.EventFactory;
import cs455.overlay.wireformats.EventManager;
import cs455.overlay.wireformats.Protocol;
import cs455.overlay.wireformats.Register;
import cs455.overlay.wireformats.RegistrationResponse;
import cs455.overlay.wireformats.RegistryEventManager;

public class Registry implements Node {

	//manages server connections on a thread
	//private ConnectionsHandler connectionsHandler;
	TCPServerThread serverThread; 

	private InetAddress locAddr; 
	private int port; 

	private int messagingNodeConnections; //how many other nodes each node is connected to. 

	EventFactory eFactory; 
	//EventManager eventManager; 

	private int completedTasks; 
	RegistryEventManager regEventManager; 


	//data structure to store all incoming connections
	private Hashtable<String, Connection> messagingNodes; 
	//manages all messaging nodes
	//inserted after being a registered connection
	//private LinkedHashMap<String, Connection> messagingNodes; 

	public Registry(int port) throws IOException {
		System.out.println("Registry constructor with port: " + port);
		serverThread = new TCPServerThread(this, port);
		serverThread.start(); 
		messagingNodes = new Hashtable<String, Connection>(); 
		completedTasks = 0; 
		this.port = port; 
		regEventManager = new RegistryEventManager(this);

		//eventManager = new EventManager(); 


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
	public synchronized void onEvent(Event event, Socket socket) throws IOException {
		System.out.println("Are we getting here?");
		System.out.println("Event type in Registry, onEvent" + event.getEventType());
		//RegistryEventManager regEventManage; 
		switch(event.getEventType()) {

		case Protocol.REGISTER_REQUEST: {
			Register rr = (Register) event;
			int success = validRegister(rr, socket);
			break; 
			//need to validate registration 
		}

		//case Protocol.REGISTRATION_RESPONSE: {
		//RegistrationResponse regresp = (RegistrationResponse) event; 
		//break; 

		//}

		case Protocol.DEREGISTER_REQUEST: {
			Deregister deregister = (Deregister) event; 
			int deregisterSuccess = validDeregister(deregister, socket);
			break;

		}

		case Protocol.TASK_COMPLETE: {
			System.out.println("TASK COMPLETE");
			if (++completedTasks == messagingNodes.size()) {
				try {
					TimeUnit.SECONDS.sleep(5);
				} catch(InterruptedException ie) {
					ie.printStackTrace();
				}
				//sendTaskSummaryRequest(); 
				break; 

			}
		}


		}

	}




	//need to validate deregistering!

	public int getPortNumber() {
		return port; 
	}

	public void setPortNumber(int portNum) {
		port = portNum; 
	}

	//registers new connection with list of connections, not with messagingNodes 
	public synchronized void registerConnection(Connection c) {
		System.out.println("Registering connection on: " + c.getName());

		if (!(messagingNodes.containsKey(c.getName()))) {
			messagingNodes.put(c.getName(), c);
		}
		else 
			System.out.println("Connection already exists.");

	}


	//removes connection from list of all connections not from messaging nodes 
	public synchronized void deregisterConnection(Connection c) {
		System.out.println("Deregistering connection on: " + c.getName());
		messagingNodes.remove(c.getName());

	}

	public byte validRegister(Register rr, Socket socket) throws IOException {
		//success = 0, failure != 0
		byte success; 

		String information;

		Connection connection = messagingNodes.get(socket.getInetAddress().getHostName() + ":" + socket.getPort());
		connection.setListeningPort(rr.getLocalPort()); //LOCAL PORT OR SERVER PORT? 


		if (rr.getIP().equals(socket.getInetAddress().getHostName())) { 
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

		RegistrationResponse response = new RegistrationResponse(information, success); 

		connection.sendData(response.getBytes());

		return success; 


	}
	public byte validDeregister(Deregister deregister, Socket socket) throws IOException {
		byte success; 
		String information = new String(); 
		Connection c = messagingNodes.get(socket.getInetAddress().getHostName() + ":" + socket.getPort());

		if (deregister.getNodeIP().equals(socket.getInetAddress().getHostName())) {
			success = 0; 
			deregisterConnection(c);
			information = "Deregistration request was successful. The number of messaging nodes currently in the overlay is: "
					+ messagingNodes.size(); 
		}
		else {
			success = 1; 
			information = "Deregistration request was unsuccessful. The IP of the request: " + 
					deregister.getNodeIP() + "doesn't match the IP of the source: " + 
					socket.getInetAddress().getHostName(); 

		}
		DeregistrationResponse dResponse = new DeregistrationResponse(); 
		dResponse.setStatus(success);
		dResponse.setInfo(information);

		c.sendData(dResponse.getBytes());


		return success;


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
			System.out.println("Registry started and listening on port " + portNumber);
		} catch (IOException e){
			System.out.println("ERROR: COULD NOT START REGISTRY");

			System.exit(0);
		}



	}

}
