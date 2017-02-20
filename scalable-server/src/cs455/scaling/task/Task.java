package cs455.scaling.task;

public interface Task extends Runnable {
	
	public void run();
	
	public String getTaskType(); 
}
