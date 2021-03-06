package cs455.overlay.wireformats;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TaskSummaryRequest implements Event, Protocol {

	
	private final int eventType = TASK_SUMMARY_REQUEST; 
	
	public TaskSummaryRequest(byte[] marshalledBytes) throws IOException {
		ByteArrayInputStream bainput = new ByteArrayInputStream(marshalledBytes);
		DataInputStream din = new DataInputStream(new BufferedInputStream(bainput));
		
		
		int type = din.readInt(); 
		if (eventType != type) {
			System.out.println("Invalid event.");
			return; 
		}
		
		bainput.close(); 
		din.close(); 
	}
	
	@Override
	public int getEventType() {
		// TODO Auto-generated method stub
		return eventType;
	}

	@Override
	public byte[] getBytes() throws IOException {
		byte[] marshalledBytes = null; 
		
		ByteArrayOutputStream baout = new ByteArrayOutputStream(); 
		DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baout));
		
		dout.writeInt(eventType);
		
		dout.flush(); 
		marshalledBytes = baout.toByteArray(); 
		
		baout.close(); 
		dout.close(); 
		return marshalledBytes;
	}

	@Override
	public Event createNewEvent(byte[] info) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
