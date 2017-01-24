package cs455.overlay.wireformats;

import java.io.IOException;

public interface Event {
	
	public int getEventType();
	
	public byte[] getBytes() throws IOException; 
	
	public Event createNewEvent(byte[] info) throws IOException;  

}
