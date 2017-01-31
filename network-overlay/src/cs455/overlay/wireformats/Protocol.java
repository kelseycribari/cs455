package cs455.overlay.wireformats;

/*Defines Event variables 
 * 
 */

public interface Protocol {

	//variables for sending messages
	public static final int REGISTER_REQUEST = 4000; 
	public static final int REGISTRATION_RESPONSE = 4001; 
	public static final int DEREGISTER_REQUEST = 4002; 
	public static final int DEREGISTRATION_RESPONSE = 4003; 
	public static final int MESSAGING_NODES_LIST = 4004; 
	public static final int LINK_WEIGHTS = 4005; 
	public static final int TASK_INITIATE = 4006; 
	public static final int TASK_COMPLETE = 4007; 
	public static final int TASK_SUMMARY_REQUEST = 4008; 
	public static final int TASK_SUMMARY_RESPONSE = 4009; 
	public static final int MESSAGE = 4010; 
	
	
}
