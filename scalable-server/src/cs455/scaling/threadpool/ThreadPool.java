package cs455.scaling.threadpool;

import cs455.scaling.task.TaskExecutor;

/*
 * Author: Kelsey Cribari 
 * Deprecated, don't need this, can just store the threads in a Thread[] inside the ThreadPoolManager. 
 * Date: 2-10-17
 */

public class ThreadPool {
	
	BlockingQueue queue; 
	
	public ThreadPool(int queueSize, int numberThreads) {
		queue = new BlockingQueue(queueSize);
		String threadName = null; 
		TaskExecutor taskExecutor = null; 
		
		
		for (int count = 0; count < numberThreads; count++) {
			threadName = "Thread-" + count; 
			taskExecutor = new TaskExecutor(queue);
			Thread thread = new Thread(taskExecutor, threadName);
			thread.start(); 
		}
	}
	
	public void submitTask(Runnable task) throws InterruptedException {
		queue.enqueue(task);
	}

}
