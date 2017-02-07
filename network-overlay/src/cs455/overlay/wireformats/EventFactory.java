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
	
	public synchronized static Event createNewEvent(byte[] info) {
		
		Event event = null; 
		
		try {
			
			ByteArrayInputStream bainput = new ByteArrayInputStream(info);
			DataInputStream din = new DataInputStream(new BufferedInputStream(bainput));
			
			int eventType = din.readInt(); 
			
			bainput.close(); 
			din.close(); 
			
			System.out.println(eventType);
			
			switch(eventType) {
			
				case Protocol.REGISTER_REQUEST: {
					event = new Register(info);
					System.out.println(event);
					
					return new Register(info);
					
					//System.out.println("Hello?");
					//System.out.println(event.getEventType());
					//return new Register(info);
					//return event; 
					
				}
				
				case Protocol.REGISTRATION_RESPONSE: {
					return new RegistrationResponse(info);
					
				}
				
				case Protocol.DEREGISTER_REQUEST: {
					return new Deregister(info);
					 
				}
				
				case Protocol.DEREGISTRATION_RESPONSE: {
					return new DeregistrationResponse(info);
					
				}
				
				/*case Protocol.MESSAGING_NODES_LIST: {
					//event = new MessagingNodesList(info);
					break;
				}
				
				case Protocol.LINK_WEIGHTS: {
					//event = new LinkWeights(info);
					break; 
				}
				
				case Protocol.TASK_INITIATE: {
					//event = new TaskInitiate(info);
					break; 
				}
				
				case Protocol.TASK_COMPLETE: {
					//event = new TaskComplete(info);
					break;
				}
				
				case Protocol.TASK_SUMMARY_REQUEST: {
					//event = new TaskSummaryRequest(info);
					break; 
				}
				
				case Protocol.TASK_SUMMARY_RESPONSE: {
					//event = new TaskSummaryResponse(info);
					break; 
				}
				*/
				case Protocol.MESSAGE: {
					return new Message(info); 
				
				}
				
				default: {
					return null; 
					 
				}
			}
			
			
			
		} catch (IOException e) {
			
		}
		return event;
		 
	}
	
	
}
