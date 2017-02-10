package cs455.overlay.transport;


import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
//import java.net.SocketException;
import java.net.SocketException;

import cs455.overlay.node.Node;
import cs455.overlay.wireformats.Event;
import cs455.overlay.wireformats.EventFactory;

public class TCPReceiverThread extends Thread {

	private Node node; 
	private Socket socket;
	private DataInputStream din; 
	//private EventFactory eFactory;
	
	public TCPReceiverThread (Socket s, Node n) throws IOException {
		socket = s; 
		node = n; 
		din = new DataInputStream(socket.getInputStream());
		//eFactory = EventFactory.getInstance(); 
		
	}
	
	@Override
	public void run() {
		
		while(socket != null) {
			try {
				int length = din.readInt(); 
				System.out.println("TCPReceiverThread run(): length:" + length); 
				
				if (length > 0) {
					byte[] data = new byte[length];
					din.readFully(data, 0, length);
					//System.out.println("TCPRecevierThread data: " + data);
					Event e = EventFactory.createEvent(data);
					System.out.println("Inside TCPReceiverThread's run." + e.getEventType());
					System.out.println(e);
					//String nodeID = new String(socket.getInetAddress().toString() + ":" + socket.getPort());
					node.onEvent(e, socket);
					//Event stuff FIXED
					
					//Message m = (Message) event; 
					//node.onEvent(m); 
				}
			} catch (SocketException s) {
				s.printStackTrace(); 
				break; 
			} catch (IOException e) {
				System.out.println(e.getMessage()); 
				break; 
			} 
		}
		
		
	}
	
}
