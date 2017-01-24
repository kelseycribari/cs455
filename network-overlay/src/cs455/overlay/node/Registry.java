package cs455.overlay.node;

import java.io.IOException;
import java.net.InetAddress;

import cs455.overlay.transport.ConnectionsHandler;
import cs455.overlay.wireformats.Event;

public class Registry implements Node {

	
	protected ConnectionsHandler connectionsHandler;
	protected InetAddress locAddr; 
	protected int port; 
	
	public Registry(int port) throws IOException {
		this.port = port; 
		//set local address 
	}
	
	public void start() throws IOException {
		connectionsHandler = new ConnectionsHandler(this, port);
		connectionsHandler.start(); 
	}
	
	@Override
	public void onEvent(Event e, String nodeIP) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main (String [] args) {
		
		if (args.length == 0) {
			System.out.println("ERROR: UNSPECIFIED PORT NUMBER");
			return;
		}
		
		//parse the number to an int 
		int portNumber = Integer.parseInt(args[0]);
		
		Registry registry = null; 
		
		
		try {
			registry = new Registry(portNumber);
			registry.start(); 
			System.out.println("Registry is listening on port: " + portNumber);
			
		} catch (IOException e){
			System.out.println("ERROR: COULD NOT START REGISTRY");
		}
		
	}

}
