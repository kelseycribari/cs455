package cs455.overlay.wireformats;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class LinkRequest implements Event, Protocol {

	private final int eventType = LINK_REQUEST; 
	
	private String IP; 
	private int port; 
	private int weight; 
	
	public LinkRequest() {
		IP = new String(); 
	}
	
	public LinkRequest(String IP, int port, int weight) {
		this.IP = IP; 
		this.port = port; 
		this.weight = weight; 
	}
	
	public LinkRequest(byte[] marshalledBytes) throws IOException {
		ByteArrayInputStream bainput = new ByteArrayInputStream(marshalledBytes); 
		DataInputStream din = new DataInputStream(new BufferedInputStream(bainput));
		
		int type = din.readInt(); 
		if (type != eventType) {
			System.out.println("Invalid event.");
			return;
		}
		
		int length = din.readInt(); 
		byte[] bytes = new byte[length];
		din.readFully(bytes);
		
		IP = new String(bytes);
		port = din.readInt(); 
		weight = din.readInt(); 
		
		
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
		
		ByteArrayOutputStream baout = new ByteArrayOutputStream(); 
		DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baout));
		
		dout.writeInt(eventType);
		
		byte[] bytes = IP.getBytes(); 
		int length = bytes.length; 
		
		dout.writeInt(length);
		dout.write(bytes);
		
		dout.writeInt(port);
		dout.writeInt(weight);
		
		dout.flush(); 
		
		marshalledBytes = baout.toByteArray(); 
		
		baout.close(); 
		dout.close(); 
		return marshalledBytes; 
		
	}

	public String getIP() {
		return IP; 
	}
	
	public void setIP(String ip) {
		IP = ip; 
	}
	
	public int getPort() {
		return port; 
		
	}
	
	public void setPort(int port) {
		this.port = port; 
	}
	
	public int getWeight() {
		return weight; 
	}
	
	public void setWeight(int weight) {
		this.weight = weight; 
	}
	
	
	@Override
	public Event createNewEvent(byte[] info) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
