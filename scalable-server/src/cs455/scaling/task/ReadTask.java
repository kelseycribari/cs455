package cs455.scaling.task;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import cs455.scaling.server.ServerStatus;
import cs455.scaling.util.ChannelUtil;

/*
 * Author: Kelsey Cribari 
 * Date: 2-21-17
 */

public class ReadTask implements Task {
	
	private SelectionKey selectionKey; 
	private int packetSize; 
	private final String TASK_TYPE = "READ TASK"; 
	ServerStatus serverStatus; 
	
	public ReadTask(SelectionKey selectionKey, int packetSize, ServerStatus serverStatus) {
		this.selectionKey = selectionKey; 
		this.packetSize = packetSize; 
		this.serverStatus = serverStatus; 
		ChannelUtil channel = (ChannelUtil) selectionKey.attachment(); 
		//synchronized(channel) { 
			channel.setIsReading(true); //synchronization moved to ChannelUtil
		//}
	}

	@Override
	public void run()  {
		//System.out.println("READTASK: run");
		SocketChannel socketChannel = (SocketChannel) selectionKey.channel(); 
		//System.out.println("Beginning to read from: " + socketChannel.socket().getInetAddress().getHostName());
		
		ByteBuffer byteBuffer = ByteBuffer.allocate(packetSize);
		int read = 0; 
		
		try {
			while(byteBuffer.hasRemaining() && read != -1) {
				read = socketChannel.read(byteBuffer);
			}
		} catch (IOException e) {
			//e.printStackTrace();
			return; 
		}
		
		if (read == -1) {
			try {
				serverStatus.removeClient(); 
				socketChannel.close();
			} catch (IOException e) {
				return;
			} 
			//return; 
		}
		
		byteBuffer.flip(); 
		byte[] packet = new byte[packetSize];
		byteBuffer.get(packet);
		
		String client = socketChannel.socket().getInetAddress().getHostName(); 
		int cPort = socketChannel.socket().getPort(); 
		//System.out.println("Read message from " + client + ":" + cPort);
		serverStatus.packetsSent(); 
		
		
		MessageDigest digest = null; 
		try {
			digest = MessageDigest.getInstance("SHA1");
			
		} catch (NoSuchAlgorithmException nsae) {
			
		}
		byte[] hash = digest.digest(packet);
		ChannelUtil util = (ChannelUtil) selectionKey.attachment(); 
		util.addBytes(hash);
		
		
		selectionKey.interestOps(SelectionKey.OP_WRITE); //SET TO READ FROM WRITE 
		util.setIsReading(false);
		
		//System.out.println("READTASK: end of run");
	}

	@Override
	public String getTaskType() {
		return TASK_TYPE;
	}

}
