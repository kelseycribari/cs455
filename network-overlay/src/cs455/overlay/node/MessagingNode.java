package cs455.overlay.node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.rmi.UnknownHostException;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Scanner;

import cs455.overlay.dijkstra.Vertex;
import cs455.overlay.transport.Connection;
import cs455.overlay.transport.ConnectionsHandler;
import cs455.overlay.transport.TCPServerThread;
import cs455.overlay.wireformats.Deregister;
import cs455.overlay.wireformats.DeregistrationResponse;
import cs455.overlay.wireformats.Event;
import cs455.overlay.wireformats.EventManager;
import cs455.overlay.wireformats.LinkRequest;
import cs455.overlay.wireformats.LinkWeights;
import cs455.overlay.wireformats.Message;
import cs455.overlay.wireformats.Protocol;
import cs455.overlay.wireformats.Register;
import cs455.overlay.wireformats.RegistrationResponse;

public class MessagingNode implements Node {

	TCPServerThread serverThread; 
	
	private Hashtable<String, Connection> connections; 
	
	private int localPort; 
	private String registryHost;
	private int registryPort; 
	private String localHostAddr; 
	private int linkWeight; 
	
	EventManager eventManager; 
	
	private int sendTracker; 
	private long sendSummation; 
	private int receiveTracker; 
	private long receiveSummation; 
	private int relayTracker; 
	
	
	private boolean registered; 
	
	//protected ConnectionsHandler connectionshandler; 
	
	
	public MessagingNode(String registryHost, int registryPort) throws IOException {
		this.registryHost = registryHost; 
		this.registryPort = registryPort; 
		
		
		serverThread = new TCPServerThread(this);
		connections = new Hashtable<String, Connection>(); 
		localPort = 0; //check this? 
		serverThread.start(); 
		
		//sets the localHostAddr by getting the InetAddress.getLocalHost().getHostName() --which converts that InetAddress into a String 
		setLocalHostAddress();
		
		sendTracker = 0; 
		sendSummation = 0; 
		receiveTracker = 0; 
		receiveSummation = 0; 
		relayTracker = 0; 
		
		eventManager = new EventManager(this);
		
		//eventManager = new EventManager(); 
		
		//try {
			//localHostAddr = InetAddress.getLocalHost().getHostName(); 
			
		//} catch (UnknownHostException u) {
			//u.printStackTrace();
		//}
		
	}
	
	public void initialize() throws IOException {
	
		
	}
	
	
	public void setLocalHostAddress() throws UnknownHostException {
		
		try {
			localHostAddr = InetAddress.getLocalHost().getHostName();
		} catch (java.net.UnknownHostException e) {
			e.printStackTrace();
		}
		
	}
	
	public String getLocalHostAddress() {
		return localHostAddr; 
	}
	
	public void setLocalPortNumber(int port) {
		localPort = port; 
	}
	
	public int getLocalPortNumber() {
		return localPort; 
	}
	
	public void setRegistryHostName(String regHost) {
		registryHost = regHost; 
	}
	
	public String getRegistryHostName() {
		return registryHost; 
	}
	
	public void setRegistryHostPort(int port) {
		registryPort = port; 
	}
	
	public int getRegistryHostPort() {
		return registryPort; 
	}
	
	public void setLinkWeight(int weight) {
		linkWeight = weight; 
	}
	
	public int getLinkWeight() {
		return linkWeight; 
	}
	
	public void setRegistered(boolean isRegistered) {
		registered = isRegistered; 
	}
	
	
	public synchronized void register() throws IOException {
		//Register register = new Register(); 
	}
	
	public synchronized void onEvent(Event event, Socket socket) {
	
		//int type = event.getEventType(); 
		
		switch(event.getEventType()) {
		
		case Protocol.REGISTER_REQUEST: {
			Register register = (Register) event; 
			try {
				validRegister(socket, register); 
			} catch (IOException e) {
				e.printStackTrace();
			}
			break; 
		}
		
		case Protocol.REGISTRATION_RESPONSE: {
			RegistrationResponse registrationResponse = (RegistrationResponse) event; 
			System.out.println("Registration Response event type: " + registrationResponse.getEventType());
			eventManager.handleRegistrationReponse(registrationResponse);
			break; 
		}
		
		case Protocol.DEREGISTRATION_RESPONSE: {
			DeregistrationResponse deregResponse = (DeregistrationResponse) event; 
			eventManager.handleDeregistrationResponse(deregResponse);
			break; 
		}
		
		case Protocol.LINK_REQUEST: {
			LinkRequest linkRequest = (LinkRequest) event; 
			
			try {
				Socket link = new Socket(linkRequest.getIP(), linkRequest.getPort());
				Connection linkConnection = new Connection(link, this);
				linkConnection.setLinkWeight(linkRequest.getWeight());
				linkConnection.setListeningPort(linkRequest.getPort());
				requestRegistration(linkConnection, linkConnection.getLinkWeight());
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			break; 
		}
		
		case Protocol.LINK_WEIGHTS: {
			LinkWeights linkWeights = (LinkWeights) event;
			//routing_registry stuff
			
			break; 
			
		}
		
		case Protocol.MESSAGE: {
			Message message = (Message) event; 
			
			LinkedList<Vertex> path = message.getPath(); 
			//retreives and removes head (first element) of the list 
			path.poll(); 
			
			if (path.isEmpty()) { //means that you've reached the destination 
				receiveTracker++; 
				receiveSummation += message.getPayload(); 
			}
			else {
				relayTracker++; 
				Vertex next = path.element(); 
				Connection c = connections.get(next.getIP() + ":" + next.getListeningPort());
				
				Message nextMessage = new Message(message.getPayload(), path);
				
				//send the message
				try {
					c.sendData(nextMessage.getBytes());
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
			break; 
		}
		
		
		
		
		}
		
			
		
		
	}
	
	public synchronized void registerConnection(Connection c) {
		connections.put(c.getName(), c);
	}
	
	public synchronized void deregisterConnection(Connection c) {
		connections.remove(c.getName());
	}
	
	
	
	public byte validRegister(Socket s, Register r) throws IOException {
		byte success; 
		
		String info; 
		
		Connection c = connections.get(s.getInetAddress().getHostName() + ":" + s.getPort());
		c.setLinkWeight(r.getLinkWeight());
		c.setListeningPort(r.getLocalPort());
		
		//replacement with the correct port 
		connections.remove(s.getInetAddress().getHostName() + ":" + s.getPort());
		connections.put(s.getInetAddress().getHostName() + ":" + r.getLocalPort(), c);
		
		
		if (r.getIP().equals(s.getInetAddress().getHostName())) {
			//if the IP of the register is equal to the source IP then successful register request 
			success = 0; 
			info = "Registration request was successful.";
		}
		else {
			//otherwise the request was unsuccessful and need to deregister the connection
			success = 1; 
			deregisterConnection(c);
			info = "Registration request was NOT successful. The IP Address of the request: " + r.getIP() + " does not match the source IP: " 
						+ s.getInetAddress().getHostName(); 
			
		}
		
		RegistrationResponse response = new RegistrationResponse(); 
		response.setStatus(success);
		response.setInfo(info);
		c.sendData(response.getBytes());
		
		return success; 
	}
	
	//send's a registration request 
	public void requestRegistration(Connection c, int linkWeight) {
		Register register = new Register(localHostAddr, localPort, -1); //include link weights
		try {
			c.sendData(register.getBytes());
		} catch (IOException e) {
			e.printStackTrace(); 
		}
	}
	
	//sends a deregistration request 
	public void deregistrationRequest(Connection c) {
		Deregister deregister = new Deregister(localPort, localHostAddr);
		
		try {
			c.sendData(deregister.getBytes());
			
		}catch (IOException e) {
			e.printStackTrace(); 
		}
	}
	
	public int getPortNumber() {
		return 0; 
	}
	
	public void setPortNumber(int portNumber) {
		
	}



	
	
	public static void main (String[] args) {
		 if (args.length != 2) {
			 System.out.println("Error: Invalid number of arguments.");
			 return; 
		 }
		 
		 //String registryHost = args[0]; 
		 int registryPort = Integer.parseInt(args[1]);
	
		 //Add a print statement to let you know what registry host and port you are using
		 
		 MessagingNode messagingNode; 
		 Socket socket = null; 
		 Connection connection = null; 
		 
		 try {
			 System.out.println("Here.");
			 InetAddress registryAddress = InetAddress.getByName(args[0]);
			 String registryName = registryAddress.getHostName(); 
			 //messagingNode.setRegistryHostName(registryName);
			 messagingNode = new MessagingNode(registryName, registryPort);
		 } catch (IOException e) {
			 System.out.println("Error starting messaging node. " + e.getMessage());
			 return; 
			 
		 }
		 System.out.println("Registry host port: " + messagingNode.getRegistryHostPort());
		 try {
			 System.out.println(messagingNode.getRegistryHostName() + messagingNode.getRegistryHostPort());
			 socket = new Socket(messagingNode.getRegistryHostName(), messagingNode.getRegistryHostPort());
			 System.out.println(socket.getPort());
			 connection = new Connection(socket, messagingNode);
			 messagingNode.requestRegistration(connection, -1); //the -1 means no link weight
			 System.out.println("Inside MessagingNode: node registered.");
			 
		 } catch (IOException e) {
			 e.printStackTrace(); 
		 }
		 
		 //Add print statement to say where the messaging node started up. 
		 
		 
		 //sets a flag so that it waits until the node is registered. 
		 while(!messagingNode.registered) {
			 
		 }
		 
		 //now ready to start accepting commands:
		 
		 Scanner scan = new Scanner(System.in);
		 boolean quit = false; 
		 
		 while (!quit) {
			 System.out.println("Ready to accept input. Please type 'help' for a help message.");
			 
			 String command = scan.nextLine(); 
			 command = command.toLowerCase(); 
			 
			 switch(command) {
			 
			 case "print-shortest-path": {
				 System.out.println("Not yet implemented."); //TODO: IMPLEMENT THIS 
				 break; 
			 }
			 
			 case "exit-overlay": {
				 System.out.println("Not yet implemented."); //TODO: IMPLEMENT THIS
				 break; 
			 }
			 
			 
			 
			 }
		 }
		 
		 
		 //Now that messaging node is registered, the system is ready to start accepting commands. 
		 BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		 while (true) {
			 try {
				 String command = reader.readLine(); 
			 } catch (IOException e) {
				 System.out.println("Error reading command. ");
				 continue; //keeps reading if there is an error 
			 }
		 }
		 
		 
		 
	}
	

}
