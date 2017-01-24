package cs455.overlay.transport;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import cs455.overlay.node.Node;

public class TCPReceiverThread extends Thread {

	protected Node node; 
	protected Socket socket;
	protected DataInputStream din; 
	
	public TCPReceiverThread (Socket s, Node n) throws IOException {
		socket = s; 
		node = n; 
		din = new DataInputStream(socket.getInputStream());
		
	}
	
	@Override
	public void run() {
		
		while(socket != null) {
			try {
				int length = din.readInt(); 
				
				if (length > 0) {
					byte[] data = new byte[length];
					din.readFully(data, 0, length);
					//Event stuff FIX
				}
			} catch (IOException e) {
				e.getMessage(); 
				break; 
			}
		}
		
		
	}
	
}
