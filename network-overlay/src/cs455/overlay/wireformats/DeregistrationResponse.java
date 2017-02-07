package cs455.overlay.wireformats;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class DeregistrationResponse implements Event, Protocol {

	
	private int eventType = DEREGISTRATION_RESPONSE; 
	private byte status; 
	private String information; 
	
	
	public DeregistrationResponse() {
		information = new String(); 
	}
	
	public DeregistrationResponse(String info, byte status) {
		this.status = status; 
		information = info; 
	}
	
	public DeregistrationResponse(byte[] marshalledBytes) throws IOException {
		//eventType = DEREGISTRATION_RESPONSE; 
		
		ByteArrayInputStream bainput = new ByteArrayInputStream(marshalledBytes);
		DataInputStream din = new DataInputStream(new BufferedInputStream(bainput));
		
		int type = din.readInt(); 
		if (type != eventType) {
			System.out.println("That is an invalid event type. "); 
			return; 
		}
		
		status = din.readByte(); 
		
		int length = din.readInt(); 
		byte[] bytes = new byte[length];
		din.readFully(bytes); 
		
		information = new String(bytes);
		
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
		dout.writeByte(status);
		
		byte[] dataBytes = information.getBytes(); 
		int length = dataBytes.length; 
		dout.writeInt(length);
		dout.write(dataBytes);
		dout.flush(); 
		
		marshalledBytes = baoutput.toByteArray(); 
		
		
		baoutput.close(); 
		dout.close(); 
		
		
		
		return marshalledBytes;
	}
	
	public byte getStatus() {
		return status; 
	}
	
	public void setStatus(byte status) {
		this.status = status; 
	}
	
	public String getInfo() {
		return information; 
	}
	
	public void setInfo(String info) {
		information = info; 
	}

	@Override
	public Event createNewEvent(byte[] info) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
