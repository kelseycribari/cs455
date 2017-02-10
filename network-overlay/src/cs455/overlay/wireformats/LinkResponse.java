package cs455.overlay.wireformats;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class LinkResponse implements Event, Protocol {

	private final int eventType = LINK_RESPONSE; 
	private String information; 
	private byte status; 
	
	public LinkResponse() {
		information = new String(); 
		
	}
	
	public LinkResponse(String additionalInfo, byte status) {
		information = additionalInfo; 
		this.status = status; 
	}
	
	public LinkResponse(byte[] marshalledBytes) throws IOException {
		ByteArrayInputStream bainput = new ByteArrayInputStream(marshalledBytes);
		DataInputStream din = new DataInputStream(new BufferedInputStream(bainput));
		
		int type = din.readInt(); 
		if (eventType != type) {
			System.out.println("Invalid event.");
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
		// TODO Auto-generated method stub
		return eventType;
	}
	
	public String getAdditionalInfo() {
		return information; 
	}
	
	public void setAdditionalInfo(String info) {
		information = info; 
	}
	
	public byte getStatus() {
		return status; 
	}
	
	public void setStatus(byte status) {
		this.status = status; 
	}

	@Override
	public byte[] getBytes() throws IOException {
		byte[] marshalledBytes = null; 
		
		ByteArrayOutputStream baout = new ByteArrayOutputStream(); 
		DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baout));
		
		dout.writeInt(eventType);
		
		dout.writeByte(status);
		
		byte[] bytes = information.getBytes(); 
		int length = bytes.length; 
		
		dout.writeInt(length);
		dout.write(bytes);
		
		dout.flush(); 
		marshalledBytes = baout.toByteArray();
		
		baout.close(); 
		dout.close(); 
		
		
		
		return marshalledBytes;
	}

	@Override
	public Event createNewEvent(byte[] info) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
