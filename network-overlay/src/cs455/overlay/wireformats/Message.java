package cs455.overlay.wireformats;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;

public class Message implements Event, Protocol {

	
	private int eventType; 
	private int msgpayload; 
	
	private int count;
	 
	private String[] path; 
	
	public Message(int payload, String[] route) {
		msgpayload = payload; 
		path = route; 
		count = path.length; 
		eventType = MESSAGE; 
	
	}
	
	public Message(byte[] marshalledBytes) throws IOException {
		ByteArrayInputStream bainput = new ByteArrayInputStream(marshalledBytes);
		DataInputStream din = new DataInputStream(new BufferedInputStream(bainput));
		
		eventType = din.readInt(); 
		msgpayload = din.readInt(); 
		count = din.readInt(); 
		path = new String[count];
		
		for (int i = 0; i < count; i++) {
			int idlength = din.readInt(); 
			byte[] idbytes = new byte[idlength];
			din.readFully(idbytes);
			path[i] = new String(idbytes);
		}
		
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
		dout.writeInt(msgpayload);
		dout.writeInt(count);
		//dout.writeInt(length);
		
		for (int i = 0; i < count; i++) {
			byte[] idbytes = path[i].getBytes(); 
			int length = idbytes.length; 
			
			dout.writeInt(length);
			dout.write(idbytes);
			
		}
		//dout.write(idbytes)
		
		dout.flush(); 
		
		marshalledBytes = baoutput.toByteArray(); 
		
		
		baoutput.close(); 
		dout.close(); 
		
		return null;
	}

	public String getDestination() {
		if (path.length > 1) {
			return path[path.length - 1];
		}
		else 
			return "Destination is undefined."; 
	}
	
	@Override
	public Event createNewEvent(byte[] info) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
