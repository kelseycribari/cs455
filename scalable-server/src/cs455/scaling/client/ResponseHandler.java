package cs455.scaling.client;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;

/*
 * Author: Kelsey Cribari 
 * Date: 2-23-17
 * Created to help the client listen for responses from the server. Stores the pending hashes here for the client. 
 */

public class ResponseHandler implements Runnable {

	//private LinkedList<String> pendingHashes; 
	private LinkedList<BigInteger> pendingHashes;
	private SocketChannel socketChannel; 
	
	private static final int CHECKSUM = 20; 


	public ResponseHandler(SocketChannel socketChannel) {
		this.socketChannel = socketChannel; 
		pendingHashes = new LinkedList<BigInteger>(); 
		//pendingHashes = new LinkedList<String>(); 
	}

//	private String SHA1FromBytes(byte[] data) throws NoSuchAlgorithmException {
//		MessageDigest digest = MessageDigest.getInstance("SHA1");
//		byte[] hash = digest.digest(data); 
//		BigInteger hashInt = new BigInteger(1, hash); 
//
//		return hashInt.toString(16); 
//	}

	public void addPending(byte[] hash) throws NoSuchAlgorithmException {
		//		
		//		try {
		//			String hash = SHA1FromBytes(data);
		BigInteger integerHash = new BigInteger(1, hash);
		synchronized(pendingHashes) {
			pendingHashes.add(integerHash); 
		}
		//			}
		//		} catch (Exception e) {
		//			e.printStackTrace();
		//		} 
		//		

	}

	@Override
	public void run() {
		
		System.out.println(Thread.interrupted());
		
		while(!Thread.interrupted()) {
			//size of the checksum = 20 
			
			ByteBuffer byteBuffer = ByteBuffer.allocate(CHECKSUM);
			int read = 0; 
			
			//only half of threads getting to this point? 
			//Now all getting to this point. 
			//System.out.println("Byte Buffer remaining: " + byteBuffer.hasRemaining() + " read: " + read);

			while (byteBuffer.hasRemaining() && read != -1) {
				try {
					//System.out.println(read);
					read = socketChannel.read(byteBuffer);
					
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
			

			byteBuffer.flip(); 
			byte[] data = new byte[CHECKSUM]; 
			byteBuffer.get(data); 
			 
			
			//Not currently getting here 
			//System.out.println("Here?");
			
			//recalculate hash to check in pending hashes 
			//convert to string for testing print message
			BigInteger temp = new BigInteger(1, data); 
			String hash = temp.toString(16); 
			
			//System.out.println("Response Handler hash: " + hash);
			boolean success;
			//if successful, means that an acknowledgement received from the server and the hashcode has been verified. Therefore it can be removed. 
			synchronized(pendingHashes) {
				success = pendingHashes.remove(temp); 
			}
			
			if (success) {
				System.out.println("Received " + hash + " from the server. Removed from pending hashes.");
			}
			else {
				System.out.println("The given hash: " + hash + " was not found in the pending hashes list.");
			}
		}

	}
	
	@Override
	public String toString() {
		return "Response Handler: " + pendingHashes.size(); 
	}

}
