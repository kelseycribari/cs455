package cs455.scaling.task;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import cs455.scaling.util.ChannelUtil;

/*
 * Author: Kelsey Cribari 
 * Date: 2-21-17
 */

public class WriteTask implements Task {
	
	private SelectionKey selectionKey; 
	private final String TASK_TYPE = "WRITE TASK"; 
	
	
	public WriteTask(SelectionKey selectionKey) {
		this.selectionKey = selectionKey; 
		ChannelUtil util = (ChannelUtil) selectionKey.attachment(); 
		util.setIsWriting(true); 
		
	}

	@Override
	public void run() {
		ChannelUtil util = (ChannelUtil) selectionKey.attachment(); 
		SocketChannel socketChannel = (SocketChannel) selectionKey.channel(); 
		
		for (byte[] b : util.getBytesWaiting()) {
			ByteBuffer byteBuffer = ByteBuffer.wrap(b); 
			try {
				socketChannel.write(byteBuffer); 
				
				BigInteger hash = new BigInteger(1, b);
				String hashString = hash.toString(16); 
				String client = socketChannel.socket().getInetAddress().getHostName(); 
				int clientPort = socketChannel.socket().getPort(); 
				System.out.println("Wrote " + hashString + " to " + client + " on " + clientPort);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		//set the interest to read
		selectionKey.interestOps(SelectionKey.OP_READ);
		util.setIsWriting(false);
		
		
	}

	@Override
	public String getTaskType() {
		return TASK_TYPE;
	}

}
