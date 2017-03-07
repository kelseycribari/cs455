package cs455.scaling.util;

import java.util.ArrayList;
import java.util.List;

/*
 * Author: Kelsey Cribari 
 * Date: 2-20-17
 * Stores information about the current channel. Can tell whether the current thread is reading or writing, and keeps track of waiting bytes
 * Intended for thread safety.
 */

public class ChannelUtil {

	
	private volatile boolean isReading; 
	private volatile boolean isWriting; 
	
	//stores the byte[] that are pending writing
	private List<byte[]> bytesWaiting; 
	
	
	public ChannelUtil() {
		isReading = false; 
		isWriting = false; 
		bytesWaiting = new ArrayList<byte[]>(); 
	}
	
	
	public void addBytes(byte[] waiting) {
		synchronized(bytesWaiting) {
			bytesWaiting.add(waiting);
		}
	}
	
	public List<byte[]> getBytesWaiting() {
		List<byte[]> waiting = new ArrayList<byte[]>(); 
		synchronized(bytesWaiting) {
			for (byte[] b : bytesWaiting){
				waiting.add(b);
			}
			bytesWaiting.clear(); 
		}
		
		
		return waiting; 
	}
	
	public void setIsReading(boolean reading) {
		isReading = reading; 
	}
	public boolean isReading() {
		return isReading; 
	}
	public void setIsWriting(boolean writing) {
		isWriting = writing; 
	}
	public boolean isWriting() {
		return isWriting; 
	}
	
}
