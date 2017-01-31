package cs455.overlay.wireformats;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Register implements Event, Protocol {

	private int eventType; 
	private String IP; 
	private int locport; 
	private int serverPort; 
	
	
	public Register(String ipaddr, int lport, int sport) {
		IP = ipaddr; 
		locport = lport; 
		serverPort = sport; 
		eventType = REGISTER_REQUEST; 
	}
	
	public Register(byte[] marshalledBytes) throws IOException {
		
		ByteArrayInputStream bainput = new ByteArrayInputStream(marshalledBytes);
		DataInputStream din = new DataInputStream(new BufferedInputStream(bainput));
		
		//get event type
		this.eventType = din.readInt(); 
		
		
		//first allocate size, then create byte array based on length 
		int length = din.readInt(); 
		byte[] idbytes = new byte[length];
		din.readFully(idbytes);
		
		IP = new String(idbytes);
		locport = din.readInt();
		serverPort = din.readInt(); 
		
		bainput.close(); 
		din.close(); 
		
	}
	
	
	
	@Override
	public int getEventType() {
		// TODO Auto-generated method stub
		return eventType;
	}

	@Override
	public byte[] getBytes() throws IOException {
		
		byte[] marshalledBytes = null; 
		
		ByteArrayOutputStream baoutput = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baoutput));
		
		//write the event type
		dout.writeInt(eventType);
		
		//get the byte array of the IP address
		byte[] idbytes = IP.getBytes(); 
		int length = idbytes.length; 
		
		//have to write the length first and then the byte[] to allocate the right amount of space
		dout.writeInt(length);
		dout.write(idbytes);
		
		dout.write(locport);
		dout.write(serverPort);
		dout.flush(); 
		
		marshalledBytes = baoutput.toByteArray(); 
		
		baoutput.close(); 
		dout.close(); 
		
		return marshalledBytes;
	}

	@Override
	public Event createNewEvent(byte[] info) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
