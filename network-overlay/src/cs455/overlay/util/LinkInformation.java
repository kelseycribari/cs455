package cs455.overlay.util;

public class LinkInformation {
	
	private int weight; 
	
	private String sourceIP; 
	private String destinationIP; 
	
	private int sourcePort; 
	private int destinationPort; 
	
	private int sourceListening; 
	private int destinationListening; 
	
	
	public LinkInformation() {
		sourceIP = new String(); 
		destinationIP = new String(); 
		
	}
	
	public LinkInformation(int weight, String sourceIP, String destinationIP, int sourcePort, int destinationPort, int sListen, int dListen) {
		this.weight = weight; 
		this.sourceIP = sourceIP; 
		this.destinationIP = destinationIP; 
		this.sourcePort = sourcePort; 
		this.destinationPort = destinationPort; 
		this.sourceListening = sListen; 
		this.destinationListening = dListen; 
		
	}
	
	public int getWeight() {
		return weight; 
	}
	
	public void setWeight(int weight) {
		this.weight = weight; 
	}
	
	public String getSourceIP() {
		return sourceIP; 
	}
	
	public void setSourceIP(String ip) {
		sourceIP = ip; 
	}
	
	public String getDestinationIP() {
		return destinationIP; 
	}
	
	public void setDestinationIP(String ip) {
		destinationIP = ip; 
	}
	
	public int getSourcePort() {
		return sourcePort; 
	}
	
	public void setSourcePort(int port) {
		sourcePort = port; 
	}
	
	public int getDestinationPort() {
		return destinationPort; 
	}
	
	public void setDestinationPort(int port) {
		destinationPort = port; 
	}
	
	public int getSourceListeningPort() {
		return sourceListening; 
	}
	
	public void setSourceListeningPort(int port) {
		sourceListening = port; 
	}
	
	public int getDestListeningPort() {
		return destinationListening; 
	}
	
	public void setDestListeningPort(int port) {
		destinationListening = port; 
	}
	
	
	

}
