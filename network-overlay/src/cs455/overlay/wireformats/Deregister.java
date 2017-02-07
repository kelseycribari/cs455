package cs455.overlay.wireformats;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Deregister implements Event, Protocol {

	
	//private int eventType;
	//IP address of the node to be deregistered
	private String nodeIP; 
	//port of the node to be deregistered
	private int nodePort; 
	private int eventType = DEREGISTER_REQUEST; 
	
	public Deregister(byte[] marshalledBytes) throws IOException {
		//eventType = DEREGISTER_REQUEST; 
		
		ByteArrayInputStream bainput = new ByteArrayInputStream(marshalledBytes); 
		DataInputStream din = new DataInputStream(new BufferedInputStream(bainput));
		
		int eType = din.readInt(); 
		if (eType != eventType) {
			System.out.println("That event type is invalid.");
			return; //exits the method if the type does not match DEREGISTER_REQUEST
			
		}
		
		int IPlength = din.readInt(); 
		byte[] IPbytes = new byte[IPlength];
		din.readFully(IPbytes);
		
		nodeIP = new String(IPbytes);
		nodePort = din.readInt(); 
		
		
		//close the bytearrayinputstream and the datainputstream
		bainput.close(); 
		din.close(); 
		
	}
	
	public Deregister(int port, String IP) {
		nodePort = port; 
		nodeIP = IP; 
		
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
		
		dout.writeInt(eventType);
		
		byte[] IPbytes = nodeIP.getBytes(); 
		int length = IPbytes.length; 
		dout.writeInt(length);
		dout.write(IPbytes);
		
		dout.writeInt(nodePort);
		
		dout.flush(); 
		marshalledBytes = baoutput.toByteArray(); 
		
		baoutput.close(); 
		dout.close(); 
		
		
		return marshalledBytes;
	}
	
	public String getNodeIP() {
		return nodeIP; 
	}
	
	public void setNodeIP(String ip) {
		nodeIP = ip; 
	}
	
	public int getNodePort() {
		return nodePort; 
	}

	public void setNodePort(int port) {
		nodePort = port; 
	}
	
	@Override
	public Event createNewEvent(byte[] info) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
