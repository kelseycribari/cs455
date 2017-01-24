package cs455.overlay.transport;

import java.net.Socket; 
import java.io.DataOutputStream; 
import java.io.IOException; 


public class TCPSender {
	
	protected DataOutputStream dout;
	
	public TCPSender(Socket s) throws IOException {
		dout = new DataOutputStream(s.getOutputStream()); 
	}

	
	public synchronized void sendData(byte[] data) throws IOException {
		
		int length = data.length; 
		//first sends length of the data 
		dout.writeInt(length);
		//then writes the actual data 
		dout.write(data);
		dout.flush(); 
	}
}
