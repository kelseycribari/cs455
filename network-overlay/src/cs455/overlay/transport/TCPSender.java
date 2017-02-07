package cs455.overlay.transport;

import java.net.Socket; 
import java.io.DataOutputStream; 
import java.io.IOException; 


public class TCPSender {
	
	private DataOutputStream dout;
	private Socket socket; 
	
	public TCPSender(Socket s) throws IOException {
		dout = new DataOutputStream(s.getOutputStream()); 
		socket = s; 
	}

	//synchronized??
	public synchronized void sendData(byte[] data) throws IOException {
		
		int length = data.length; 
		//first sends length of the data 
		dout.writeInt(length);
		//then writes the actual data 
		dout.write(data, 0, length);
		dout.flush(); 
	}
}
