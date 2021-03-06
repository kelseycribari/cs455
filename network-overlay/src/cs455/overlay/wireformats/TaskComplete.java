package cs455.overlay.wireformats;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TaskComplete implements Event, Protocol {

	private final int eventType = TASK_COMPLETE; 
	private int port; 
	private String IP; 
	
	
	public TaskComplete() {
		IP = new String(); 
	}
	
	public TaskComplete(int port, String IP) {
		this.port = port; 
		this.IP = IP; 
	}
	
	public TaskComplete(byte[] marshalledBytes) throws IOException {
		ByteArrayInputStream bainput = new ByteArrayInputStream(marshalledBytes);
		DataInputStream din = new DataInputStream(new BufferedInputStream(bainput));
		
		
		int type = din.readInt(); 
		if (eventType != type) {
			System.out.println("Invalid event.");
			return; 
		}
		
		int length = din.readInt(); 
		byte[] bytes = new byte[length];
		din.readFully(bytes);
		
		IP = new String(bytes);
		port = din.readInt(); 
		
		
		bainput.close(); 
		din.close(); 
	}
	
	@Override
	public int getEventType() {
		
		return eventType;
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
		dout.flush(); 
		
		marshalledBytes = baout.toByteArray(); 
		//need to do this twice?? 
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
