package cs455.overlay.wireformats;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cs455.overlay.util.LinkInformation; 

public class LinkWeights implements Event, Protocol {
	
	private final int eventType = LINK_WEIGHTS; 
	
	private List<LinkInformation> links; 
	private int numberLinks; 

	
	public LinkWeights() {
		links = new ArrayList<LinkInformation>(); 
	}
	
	public LinkWeights(List<LinkInformation> links, int count) {
		this.links = links; 
		numberLinks = count; 
	}
	
	public LinkWeights(byte[] marshalledBytes) throws IOException {
		ByteArrayInputStream bainput = new ByteArrayInputStream(marshalledBytes);
		DataInputStream din = new DataInputStream(new BufferedInputStream(bainput));
		
		int type = din.readInt();
		if (type != eventType) {
			System.out.println("Invalid event.");
			return; 
		}
		
		numberLinks = din.readInt(); 
		links = new ArrayList<LinkInformation>(); 
		
		for (int i = 0; i < numberLinks; i++) {
			int sourceIPLength = din.readInt(); 
			byte[] sourceBytes = new byte[sourceIPLength];
			din.readFully(sourceBytes);
			
			String sourceIP = new String(sourceBytes);
			int sourcePort = din.readInt(); 
			int sourceListeningPort = din.readInt(); 
			
			int destinationIPLength = din.readInt(); 
			byte[] destinationBytes = new byte[destinationIPLength];
			din.readFully(destinationBytes);
			
			String destinationIP = new String(destinationBytes);
			int destinationPort = din.readInt(); 
			int destinationListeningPort = din.readInt(); 
			
			int lWeight = din.readInt(); 
			
			links.add(new LinkInformation(lWeight, sourceIP, destinationIP, sourcePort, destinationPort, sourceListeningPort, destinationListeningPort));
		}
		bainput.close(); 
		din.close(); 
		
		
	}
	@Override
	public int getEventType() {
		// TODO Auto-generated method stub
		return eventType;
	}
	
	public List<LinkInformation> getLinks() {
		return links; 
	}
	public void setLinks(List<LinkInformation> links) {
		this.links = links; 
	}
	
	public int getNumberLinks() {
		return numberLinks; 
	}
	
	public void setNumberLinks(int number) {
		numberLinks = number; 
	}

	@Override
	public byte[] getBytes() throws IOException {
		byte[] marshalledBytes = null; 
		
		
		ByteArrayOutputStream baout = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baout));
		
		dout.writeInt(eventType);
		dout.writeInt(links.size());
		
		for (LinkInformation info : links) {
			byte[] sourceBytes = info.getSourceIP().getBytes(); 
			int length = sourceBytes.length; 
			dout.writeInt(length);
			dout.write(sourceBytes);
			
			dout.writeInt(info.getSourcePort());
			dout.writeInt(info.getSourceListeningPort());
			
			
			byte[] destinationBytes = info.getDestinationIP().getBytes(); 
			length = destinationBytes.length; 
			dout.writeInt(length);
			dout.write(destinationBytes);
			
			dout.writeInt(info.getDestinationPort());
			dout.writeInt(info.getDestListeningPort());
			
			dout.writeInt(info.getWeight());
			
			
		}
		
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
