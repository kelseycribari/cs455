package cs455.overlay.node;

import java.io.IOException;
import java.net.InetAddress;

import cs455.overlay.transport.ConnectionsHandler;
import cs455.overlay.wireformats.Event;

public class MessagingNode implements Node {

	
	protected int localPort; 
	protected String regHost;
	protected int regPort; 
	protected InetAddress locAddr; 
	
	protected ConnectionsHandler connectionshandler; 
	
	
	public MessagingNode(String registryHost, int registryPort) throws IOException {
		regPort = registryPort; 
		regHost = registryHost; 
		//InetAddress iadr = InetAddress.getLocalHost();
		locAddr = InetAddress.getLocalHost(); 
	}
	
	
	
	
	
	public void onEvent(Event e, String nodeIP) {
	
		
	}
	
	

}
