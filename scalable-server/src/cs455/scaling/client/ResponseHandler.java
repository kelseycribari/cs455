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

	private LinkedList<String> pendingHashes;
	private SocketChannel socketChannel; 


	public ResponseHandler(SocketChannel socketChannel) {
		this.socketChannel = socketChannel; 
		pendingHashes = new LinkedList<String>(); 
	}

	private String SHA1FromBytes(byte[] data) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA1");
		byte[] hash = digest.digest(data); 
		BigInteger hashInt = new BigInteger(1, hash); 

		return hashInt.toString(16); 
	}

	public void addPending(String hash) throws NoSuchAlgorithmException {
		//		
		//		try {
		//			String hash = SHA1FromBytes(data);
		synchronized(pendingHashes) {
			pendingHashes.add(hash); 
		}
		//			}
		//		} catch (Exception e) {
		//			e.printStackTrace();
		//		} 
		//		

	}

	@Override
	public void run() {
		while(!Thread.interrupted()) {
			//size of the checksum = 20 
			ByteBuffer byteBuffer = ByteBuffer.allocate(20);
			int read = 0; 

			while (byteBuffer.hasRemaining() && read != -1) {
				try {
					read = socketChannel.read(byteBuffer);
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}

			byteBuffer.flip(); 
			byte[] data = new byte[20]; 
			byteBuffer.get(data); 
			boolean success; 

			BigInteger temp = new BigInteger(1, data); 
			String hash = temp.toString(16); 
			//if successful, means that an acknowledgement received from the server and the hashcode has been verified. Therefore it can be removed. 
			synchronized(pendingHashes) {
				success = pendingHashes.remove(hash); 
			}

			if (success) {
				System.out.println("Removed " + hash);
			}
			else {
				System.out.println("The given hash: " + hash + " was not found in the pending hashes list.");
			}
		}

	}

}
