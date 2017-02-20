package cs455.scaling.task;

import cs455.scaling.threadpool.BlockingQueue;

public class TaskExecutor implements Runnable {
	
	BlockingQueue queue; 
	
	public TaskExecutor(BlockingQueue queue) {
		this.queue = queue; 
	}
	
	@Override
	public void run() {
		try {
			while(true) {
				String name = Thread.currentThread().getName(); 
				Runnable task = (Runnable) queue.dequeue(); //had to cast to runnable because it returns an object --see if this works 
				System.out.println("Task started by Thread: " + name);
				task.run(); 
				System.out.println("Task finished by Thread: " + name);
				
			}
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}

}
