package cs455.scaling.task;

public class TestTask implements Runnable {
	
	private int number;
	
	public TestTask(int number) {
		this.number = number; 
	}
	
	

	@Override
	public void run() {
		
		System.out.println("Start execution of task number: " + number);
		
		try {
			Thread.sleep(500);
			
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
		System.out.println("End of execution of task number: " + number); 
		
	} 
	

}
