package cs455.overlay.node;

import java.io.IOException;
import java.net.Socket;

import cs455.overlay.transport.Connection;
import cs455.overlay.wireformats.Event;

public interface Node {

	public void onEvent(Event event, Socket socket) throws IOException;
	
	//public int getPortNumber(); 
	
	//public void setPortNumber(int portNumber);
	
	public void registerConnection(Connection c); 
	
	public void deregisterConnection(Connection c);
}
