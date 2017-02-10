package cs455.overlay.wireformats;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TaskSummaryResponse implements Event, Protocol {

	private final int eventType = TASK_SUMMARY_RESPONSE; 
	
	private String IP; 
	private int port; 
	
	private long sendSummation; 
	private int sendTracker; 
	private long receiveSummation; 
	private int receiveTracker; 
	private int relayTracker; 
	
	public TaskSummaryResponse() {
		IP = new String(); 
	}
	
	public TaskSummaryResponse(String IP, int port, long sendSummation, int sendTracker, long receiveSummation, int receiveTracker, int relayTracker) {
		this.IP = IP; 
		this.port = port; 
		this.sendSummation = sendSummation; 
		this.sendTracker = sendTracker; 
		this.receiveSummation = receiveSummation; 
		this.receiveTracker = receiveTracker; 
		this.relayTracker = relayTracker; 
	}
	
	public TaskSummaryResponse(byte[] marshalledBytes) throws IOException{
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
		sendTracker = din.readInt(); 
		sendSummation = din.readLong(); 
		receiveTracker = din.readInt(); 
		receiveSummation = din.readLong(); 
		relayTracker = din.readInt(); 
		
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
	public int getSendTracker() {
		return sendTracker; 
	}
	public void setSendTracker(int track) {
		sendTracker = track; 
	}
	public long getSendSummation() {
		return sendSummation; 
	}
	public void setSendSummation(long summation) {
		sendSummation = summation; 
	}
	public int getReceiveTracker() {
		return receiveTracker;
	}
	public void setReceiveTracker(int tracker) {
		receiveTracker = tracker; 
	}
	public long getReceiveSummation() {
		return receiveSummation; 
	}
	public void setReceiveSummation(long summation) {
		receiveSummation = summation; 
	}
	public int getRelayTracker() {
		return relayTracker; 
	} 
	public void setRelayTracker(int tracker) {
		relayTracker = tracker; 
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
		dout.writeInt(sendTracker);
		dout.writeLong(sendSummation);
		dout.writeInt(receiveTracker);
		dout.writeLong(receiveSummation);
		dout.writeInt(relayTracker);
		
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
