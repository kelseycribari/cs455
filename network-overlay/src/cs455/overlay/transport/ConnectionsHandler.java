package cs455.overlay.transport;



import cs455.overlay.node.Node;

import java.io.IOException;
import java.net.Socket; 
import java.net.ServerSocket; 



/*ConnectionsHandler's job is to listen for connections and to figure out how to handle them. 
 * Creates a new server thread for each incoming connection.
 */

public class ConnectionsHandler extends Thread {

	protected ServerSocket ss; 
	protected Node node; 
	protected int locport; 
	
	
	//constructor if not given the port 
	//sets the local port to the local port of the server socket 
	public ConnectionsHandler(Node n) throws IOException {
		node = n; 
		try {
			ss = new ServerSocket(0); 
			//if port not given, sets the local port to serversocket's local port
			locport = ss.getLocalPort(); 
		} catch (IOException e) { //exit if there is an error in creating the serversocket
			throw e; 
		}
	}
	
	//constructor if given the port 
	//sets the local port to the port given
	public ConnectionsHandler(Node n, int port) throws IOException {
		locport = port; 
		node = n; 
		
		try {
			ss = new ServerSocket(port);
		} catch (IOException exception) { //throws exception and exits with error creating server socket 
			throw exception; 
		}
	}
	
	
	public void run() {
		//start accepting connections here 
		
		while(true) {
			try {
				Socket s = ss.accept(); 
			} catch (IOException e) {
				e.printStackTrace();
				continue; //if one of the connections fails, print stack trace and keep going through the loop
			}
		}
		
		
	}
	
	public int getLocPort() {
		return locport; 
	}
	
}
