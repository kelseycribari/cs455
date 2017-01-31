package cs455.overlay.node;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.rmi.UnknownHostException;
import java.util.Hashtable;

import cs455.overlay.transport.Connection;
import cs455.overlay.transport.ConnectionsHandler;
import cs455.overlay.transport.TCPServerThread;
import cs455.overlay.wireformats.Event;

public class MessagingNode implements Node {

	TCPServerThread serverThread; 
	
	private Hashtable<String, Connection> connections; 
	
	private int localPort; 
	private String registryHost;
	private int registryPort; 
	private String localHostAddr; 
	
	//protected ConnectionsHandler connectionshandler; 
	
	
	public MessagingNode(String registryHost, int registryPort) throws IOException {
		//regPort = registryPort; 
		//regHost = registryHost; 
		//InetAddress iadr = InetAddress.getLocalHost();
		//locAddr = InetAddress.getLocalHost(); 
		
		serverThread = new TCPServerThread(this);
		connections = new Hashtable<String, Connection>(); 
		localPort = 0; 
		
		
		setLocalHostAddress(); 
		//try {
			//localHostAddr = InetAddress.getLocalHost().getCanonicalHostName(); 
			
		//} catch (UnknownHostException u) {
			//u.printStackTrace();
		//}
		
	}
	
	
	public void setLocalHostAddress() throws UnknownHostException {
		
		try {
			localHostAddr = InetAddress.getLocalHost().getCanonicalHostName();
		} catch (java.net.UnknownHostException e) {
			e.printStackTrace();
		}
		
	}
	
	public String getLocalHostAddress() {
		return localHostAddr; 
	}
	
	
	
	
	public void onEvent(Event event, Socket socket) {
	
		
	}
	
	public int getPortNumber() {
		return 0; 
	}
	
	public void setPortNumber(int portNumber) {
		
	}





	@Override
	public void registerConnection(Connection c) {
		// TODO Auto-generated method stub
		
	}





	@Override
	public void deregisterConnection(Connection c) {
		// TODO Auto-generated method stub
		
	}
	
	

}
