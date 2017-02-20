package cs455.scaling.threadpool;

import cs455.scaling.task.TaskExecutor;

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
