package cs455.scaling.threadpool;

import java.util.LinkedList;

import cs455.scaling.task.Task;

public class ThreadPoolManager {
	
	private Thread[] threadPool; 
	private LinkedList<Task> blockingQueue; 
	private int numberThreads; 
	
	
	public ThreadPoolManager(int numberThreads) {
		this.numberThreads = numberThreads; 
		
		threadPool = new Thread[this.numberThreads];
		for (int i = 0; i < this.numberThreads; i++) {
			threadPool[i] = new Thread(new Handler(blockingQueue));
			
		}	
	}
	
	/*
	 * initializes the threadPoolManager, and only starts the threads if the threadPoolManager hasn't already been initialized 
	 */
	public void inititalize() {
		synchronized(threadPool) {
			//checks to see if this threadPoolManager has already been initialized by checking the thread state
			//if it has, then this method does nothing 
			if (threadPool[0].getState() == Thread.State.NEW) {
				startThreads(); 
			}
		}
	}
	
	/*
	 * starts the threads in the thread pool
	 */
	public void startThreads() {
		
		for (Thread thread : threadPool) {
			thread.start(); 
		}
	}
	
	/*
	 * Adds the given thread to a queue where it will wait for an available handler thread to execute it. 
	 */
	
	public void queueTask(Task task) {
		synchronized(blockingQueue) {
			blockingQueue.addLast(task);
			blockingQueue.notify(); 
		}
	}

}
