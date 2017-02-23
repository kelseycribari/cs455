package cs455.scaling.task;

/*
 * Author: Kelsey Cribari 
 * Date: 2-19-17
 */

public interface Task extends Runnable {
	
	@Override
	public void run();
	
	public String getTaskType(); 
}
