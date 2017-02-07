package cs455.overlay.wireformats;

import cs455.overlay.node.MessagingNode;

public class EventManager {

	private final MessagingNode messagingNode; 
	
	public EventManager(MessagingNode node) {
		messagingNode = node; 
	}
	
	public void handleRegistrationReponse(RegistrationResponse response) {
		byte status = response.getStatus(); 
		if (status != 0) {
			System.err.println("Registration failed.");
			
		}
		else {
			System.out.println("Registration successful.");
			messagingNode.setRegistered(true);
		}
	}
	
	public void handleDeregistrationResponse(DeregistrationResponse dResponse) {
		byte status = dResponse.getStatus(); 
		
		if (status != 0) {
			System.err.println("Deregistration failed.");
		}
		else {
			System.out.println("Deregistration successful." );
			messagingNode.setRegistered(false);
		}
	}
	
}
