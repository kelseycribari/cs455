package cs455.overlay.wireformats;

import java.io.IOException;

public class Deregister implements Event, Protocol {

	
	private int eventType; 
	
	
	public Deregister(byte[] marshalledBytes) throws IOException {
		eventType = DEREGISTER_REQUEST; 
		
	}
	@Override
	public int getEventType() {
		// TODO Auto-generated method stub
		return eventType;
	}

	@Override
	public byte[] getBytes() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Event createNewEvent(byte[] info) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
