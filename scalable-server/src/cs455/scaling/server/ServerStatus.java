package cs455.scaling.server;

import java.util.concurrent.atomic.AtomicLong;

public class ServerStatus {
	
	private final AtomicLong clientsConnected = new AtomicLong(); 
	private final AtomicLong packetsSent = new AtomicLong(); 
	private final AtomicLong mostRecentPacketCount = new AtomicLong(); 
	private final AtomicLong lastStartTime = new AtomicLong(); 
	private final long start; 
	//private final AtomicLong currentPacketsSent = new AtomicLong(); 
	
	public ServerStatus () {
		lastStartTime.set(System.currentTimeMillis());
		mostRecentPacketCount.set(0);
		start = System.currentTimeMillis();
		
	}
	
	public void addClient() {
		clientsConnected.incrementAndGet();
	}
	public void removeClient() {
		clientsConnected.decrementAndGet();
	}
	public void packetsSent() {
		packetsSent.incrementAndGet();
	}
	
	public long getPacketsSent() {
		long ret = packetsSent.get(); 
		packetsSent.set(0);
		return ret; 
	}
	public long getConnectedClients() {
		return clientsConnected.get(); 
	}
	public int getPacketsPerSecond() {
		long s = lastStartTime.getAndSet(System.currentTimeMillis());
		long temp = System.currentTimeMillis() - s;
		if (temp == 0) {
			temp = s;
		}
		System.out.println(temp);
		return (int)(packetsSent.getAndSet(0) / ((temp / 1000)));
	}
//	public TimeBetween getTimeSpanned() {
//		return new TimeBetween(start, System.currentTimeMillis());
//	}

}
