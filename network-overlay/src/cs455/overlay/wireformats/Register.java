package cs455.overlay.wireformats;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Register implements Event, Protocol {

	private int eventType = REGISTER_REQUEST; 
	private String IP; 
	private int locport; 
	//private int serverPort;
	private int linkWeight; 
	
	
	public Register(String IPaddr, int lPort) {
		IP = IPaddr; 
		locport = lPort; 
		//serverPort = sPort; 
		//eventType = REGISTER_REQUEST; 
		System.out.println("Register Request created!");
	}
	
	public Register(byte[] marshalledBytes) throws IOException {
		
		System.out.println("Register constructor with byte[]");
		ByteArrayInputStream bainput = new ByteArrayInputStream(marshalledBytes);
		DataInputStream din = new DataInputStream(new BufferedInputStream(bainput));
		
		//get event type
		this.eventType = din.readInt(); 
		
		System.out.println(eventType);
		//first allocate size, then create byte array based on length 
		int length = din.readInt(); 
		byte[] idbytes = new byte[length];
		din.readFully(idbytes);
		
		IP = new String(idbytes);
		locport = din.readInt();
		Register register = new Register(IP, locport);
		//serverPort = din.readInt(); 
		
		bainput.close(); 
		din.close(); 
	}
	
	
	public String getIP() {
		return IP; 
	}
	
	public void setIP(String IP) {
		this.IP = IP; 
	}
	
	
	public int getLocalPort() {
		return locport; 
	}
	
	public void setLocalPort(int port) {
		locport = port; 
	}
	//public int getServerPort() {
		//return serverPort; 
	//}
	public void setLinkWeight(int weight) {
		linkWeight = weight; 
	}
	
	public int getLinkWeight() {
		return linkWeight; 
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
		
		//write the event type
		dout.writeInt(eventType);
		
		//get the byte array of the IP address
		byte[] idbytes = IP.getBytes(); 
		int length = idbytes.length; 
		
		//have to write the length first and then the byte[] to allocate the right amount of space
		dout.writeInt(length);
		dout.write(idbytes);
		
		dout.write(locport);
		//dout.write(serverPort);
		dout.flush(); 
		
		marshalledBytes = baoutput.toByteArray(); 
		
		baoutput.close(); 
		dout.close(); 
		
		return marshalledBytes;
	}

	@Override
	public Event createNewEvent(byte[] marshalledBytes) throws IOException {
		
		
		//return register;
		return null; 
	}

}
