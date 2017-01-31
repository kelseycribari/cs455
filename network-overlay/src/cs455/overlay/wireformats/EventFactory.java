package cs455.overlay.wireformats;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class EventFactory {

	private static final EventFactory eFactory = new EventFactory(); 
	
	public static EventFactory getInstance() {
		return eFactory; 
	}
	
	public static Event createNewEvent(byte[] info) {
		
		Event event = null; 
		
		try {
			
			ByteArrayInputStream bainput = new ByteArrayInputStream(info);
			DataInputStream din = new DataInputStream(new BufferedInputStream(bainput));
			
			int eventType = din.readInt(); 
			
			din.close(); 
			bainput.close(); 
			
			
			switch(eventType) {
			
				case Protocol.REGISTER_REQUEST: {
					event = new Register(info);
					break;
				}
				
				case Protocol.REGISTRATION_RESPONSE: {
					event = new RegistrationResponse(info);
					break;
				}
				
				case Protocol.DEREGISTER_REQUEST: {
					event = new Deregister(info);
					break; 
				}
				
				case Protocol.DEREGISTRATION_RESPONSE: {
					event = new DeregistrationResponse(info);
					break; 
				}
				
				case Protocol.MESSAGING_NODES_LIST: {
					event = new MessagingNodesList(info);
					break;
				}
				
				case Protocol.LINK_WEIGHTS: {
					event = new LinkWeights(info);
					break; 
				}
				
				case Protocol.TASK_INITIATE: {
					event = new TaskInitiate(info);
					break; 
				}
				
				case Protocol.TASK_COMPLETE: {
					event = new TaskComplete(info);
					break;
				}
				
				case Protocol.TASK_SUMMARY_REQUEST: {
					event = new TaskSummaryRequest(info);
					break; 
				}
				
				case Protocol.TASK_SUMMARY_RESPONSE: {
					event = new TaskSummaryResponse(info);
					break; 
				}
				
				case Protocol.MESSAGE: {
					event = new Message(info); 
					break; 
				}
				
				default: {
					break; 
				}
			}
			
			
		} catch (IOException e) {
			
		}
		return event; 
	}
	
	
}
