package cs455.scaling.task;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.List;

import cs455.scaling.server.ServerStatus;
import cs455.scaling.util.ChannelUtil;

/*
 * Author: Kelsey Cribari 
 * Date: 2-21-17
 */

public class WriteTask implements Task {

	private SelectionKey selectionKey; 
	private final String TASK_TYPE = "WRITE TASK"; 
	ServerStatus serverStatus; 


	public WriteTask(SelectionKey selectionKey, ServerStatus serverStatus) {
		this.selectionKey = selectionKey; 
		this.serverStatus = serverStatus; 
		ChannelUtil util = (ChannelUtil) selectionKey.attachment(); 
		util.setIsWriting(true); 

	}

	@Override
	public void run() {
		//System.out.println("WRITETASK: run");
		ChannelUtil util = (ChannelUtil) selectionKey.attachment(); 

		List<byte[]> bytesWaiting = util.getBytesWaiting(); 
		SocketChannel socketChannel = (SocketChannel) selectionKey.channel(); 

		for (byte[] b : bytesWaiting) {
			ByteBuffer byteBuffer = ByteBuffer.wrap(b); 
			try {
				socketChannel.write(byteBuffer); 
			} catch (IOException e) {
				
				try {
					serverStatus.removeClient(); 
					socketChannel.close();
					return; 
				} catch (IOException e1) {
				
				} 
			
			}
			
			BigInteger hash = new BigInteger(1, b);
			String hashString = hash.toString(16); 
			String client = socketChannel.socket().getInetAddress().getHostName(); 
			int clientPort = socketChannel.socket().getPort(); 
			//System.out.println("Wrote " + hashString + " to " + client + ":" + clientPort);
			serverStatus.packetsSent(); 


		}

		//set the interest to read
		selectionKey.interestOps(SelectionKey.OP_READ);//
		util.setIsWriting(false);

		//System.out.println("WRITETASK: end of run.");

	}

	@Override
	public String getTaskType() {
		return TASK_TYPE;
	}

}
