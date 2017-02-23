package cs455.scaling.threadpool;

import java.util.LinkedList;

import cs455.scaling.task.Task;

/*
 * Author: Kelsey Cribari 
 * Date: 2-20-17
 * Worker waits for a task to be available in the readyTasksQueue and once available, removes from the queue and runs it. 
 */

public class Worker implements Runnable {

	//private BlockingQueue readyTasksQueue; 
	private LinkedList<Task> readyTasksQueue; 
	
	
	public Worker(LinkedList<Task> queue) {
		readyTasksQueue = queue; 
		//readyTasksQueue = new BlockingQueue(); 
	}
	
	
	/*
	 * Waits for task to be ready, when ready, dequeues the task and executes it. Then the handler returns to waiting for tasks. 
	 */
	@Override
	public void run() {
		
		while(!(Thread.interrupted())) {
			Task task; 
			//adding synchronization makes it so you don't need blocking queue object 
			synchronized(readyTasksQueue) {
				while(readyTasksQueue.isEmpty()) {
					try {
						readyTasksQueue.wait(); 
					} catch(InterruptedException e) {
						e.printStackTrace(); 
						return; 
					}
				}
				
				task = readyTasksQueue.removeFirst(); 
			}
			task.run(); 
			
		}
	}

	
}
