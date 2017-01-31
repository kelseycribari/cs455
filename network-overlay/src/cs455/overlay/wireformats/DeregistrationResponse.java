package cs455.overlay.wireformats;

import java.io.IOException;

public class DeregistrationResponse implements Event, Protocol {

	
	private int eventType; 
	
	public DeregistrationResponse(byte[] marshalledBytes) throws IOException {
		eventType = DEREGISTRATION_RESPONSE; 
		
	}
	
	@Override
	public int getEventType() {
		// TODO Auto-generated method stub
		return 0;
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
