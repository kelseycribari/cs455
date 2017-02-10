package cs455.overlay.wireformats;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;

import cs455.overlay.dijkstra.Vertex;

public class Message implements Event, Protocol {

	
	private int eventType = MESSAGE; 
	private int msgpayload; 
	
	//private int count;
	 
	private LinkedList<Vertex> path; 
	
	public Message() {
		path = new LinkedList<Vertex>(); 
	}
	
	public Message(int payload, LinkedList<Vertex> route) {
		msgpayload = payload; 
		path = route; 
		//count = path.length; 
		//eventType = MESSAGE; 
	
	}
	
	public Message(byte[] marshalledBytes) throws IOException {
		ByteArrayInputStream bainput = new ByteArrayInputStream(marshalledBytes);
		DataInputStream din = new DataInputStream(new BufferedInputStream(bainput));
		
		eventType = din.readInt(); 
		msgpayload = din.readInt(); 
		int count = din.readInt(); 
		//path = new String[count];
		
		for (int i = 0; i < count; i++) {
			int idlength = din.readInt(); 
			byte[] idbytes = new byte[idlength];
			din.readFully(idbytes);
			Vertex v = new Vertex(idbytes);
			path.add(v);
		}
		
		bainput.close(); 
		din.close(); 
		
	}
	@Override
	public int getEventType() {
		
		return eventType;
	}
	
	public int getPayload() {
		return msgpayload; 
	}

	public void setPayload(int payload) {
		msgpayload = payload; 
	}
	
	public LinkedList<Vertex> getPath() {
		return path; 
	}
	
	public void setPath(LinkedList<Vertex> path) {
		this.path = path; 
	}
	@Override
	public byte[] getBytes() throws IOException {
		byte[] marshalledBytes = null; 
		
		ByteArrayOutputStream baoutput = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baoutput));
		
		dout.writeInt(eventType);
		dout.writeInt(msgpayload);
		dout.writeInt(path.size());
		//dout.writeInt(length);
		
		for (Vertex v : path) {
			byte[] idbytes = v.getBytes(); 
			int length = idbytes.length; 
			dout.writeInt(length);
			dout.write(idbytes);
		}
	
		//dout.write(idbytes)
		
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
