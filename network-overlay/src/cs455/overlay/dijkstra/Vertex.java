package cs455.overlay.dijkstra;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Vertex {

	
	private String IP; 
	private int port; 
	private int listeningPort; 
	
	
	public Vertex(String IP, int port) {
		this.IP = IP; 
		this.port = port; 
	}
	
	
	public Vertex(byte[] marshalledBytes) throws IOException {
		ByteArrayInputStream bainput = new ByteArrayInputStream(marshalledBytes);
		DataInputStream din = new DataInputStream(new BufferedInputStream(bainput));
		
		int IPLength = din.readInt(); 
		byte[] IPBytes = new byte[IPLength];
		din.readFully(IPBytes);
		
		IP = new String(IPBytes);
		port = din.readInt(); 
		
		listeningPort = din.readInt(); 
		
		
		bainput.close(); 
		din.close(); 
	}
	
	public byte[] getBytes() throws IOException {
		byte[] marshalledBytes = null; 
		
		ByteArrayOutputStream baout = new ByteArrayOutputStream(); 
		DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baout));
		
		byte[] IPbytes = IP.getBytes(); 
		int length = IPbytes.length; 
		dout.writeInt(length);
		dout.write(IPbytes);
		
		dout.writeInt(port);
		dout.writeInt(listeningPort);
		
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
	
	public int getListeningPort() {
		return listeningPort; 
	}
	
	public void setListeningPort(int port) {
		listeningPort = port; 
	}
}
