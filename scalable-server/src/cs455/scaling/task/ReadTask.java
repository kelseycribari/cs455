package cs455.scaling.task;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import cs455.scaling.util.ChannelUtil;

/*
 * Author: Kelsey Cribari 
 * Date: 2-21-17
 */

public class ReadTask implements Task {
	
	private SelectionKey selectionKey; 
	private int packetSize; 
	private final String TASK_TYPE = "READ TASK"; 
	
	public ReadTask(SelectionKey selectionKey, int packetSize) {
		this.selectionKey = selectionKey; 
		this.packetSize = packetSize; 
		
		ChannelUtil channel = (ChannelUtil) selectionKey.attachment(); 
		//synchronized(channel) { 
			channel.setIsReading(true); //synchronization moved to ChannelUtil
		//}
	}

	@Override
	public void run() {
		SocketChannel socketChannel = (SocketChannel) selectionKey.channel(); 
		System.out.println("Beginning to read from: " + socketChannel.socket().getInetAddress().getHostName());
		
		ByteBuffer byteBuffer = ByteBuffer.allocate(packetSize);
		int read = 0; 
		
		try {
			while(byteBuffer.hasRemaining() && read != -1) {
				read = socketChannel.read(byteBuffer);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return; 
		}
		
		if (read == -1) {
			return; 
		}
		
		byteBuffer.flip(); 
		byte[] packet = new byte[packetSize];
		byteBuffer.get(packet);
	}

	@Override
	public String getTaskType() {
		return TASK_TYPE;
	}

}
