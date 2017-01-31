package cs455.overlay.wireformats;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class RegistrationResponse implements Event, Protocol {

	
	private int eventType; 
	private String information; 
	private byte status; 
	
	public RegistrationResponse(String info, byte status) {
		information = info; 
		this.status = status; 
		eventType = REGISTRATION_RESPONSE; 
	}
	
	public RegistrationResponse(byte[] marshalledBytes) throws IOException {
		
		ByteArrayInputStream bainput = new ByteArrayInputStream(marshalledBytes);
		DataInputStream din = new DataInputStream(new BufferedInputStream(bainput));
		
		//get event type from datainputstream
		eventType = din.readInt(); 
		
		//get length
		int length = din.readInt();
		byte[] idbytes = new byte[length];
		din.readFully(idbytes);
		
		information = new String(idbytes);
		status = din.readByte(); 
		
		bainput.close(); 
		din.close();
		
	}
	
	
	@Override
	public int getEventType() {
		
		return eventType;
	}

	@Override
	public byte[] getBytes() throws IOException {
		
		byte[] marshalledBytes = null; 
		
		ByteArrayOutputStream baoutput = new ByteArrayOutputStream(); 
		DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baoutput));
		
		dout.writeInt(eventType);
		
		
		byte[] idbytes = information.getBytes(); 
		int length = idbytes.length; 
		dout.writeInt(length);
		dout.write(idbytes);
		
		dout.writeByte(status);
		
		dout.flush(); 
		
		marshalledBytes = baoutput.toByteArray(); 
		
		baoutput.close(); 
		dout.close(); 
		
		return marshalledBytes;
	}

	@Override
	public Event createNewEvent(byte[] info) throws IOException {
		return null; 
	}

}
