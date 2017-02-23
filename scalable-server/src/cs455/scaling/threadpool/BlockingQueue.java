package cs455.scaling.threadpool;

import java.util.LinkedList;
import java.util.Queue;


/*
 * Author: Kelsey Cribari 
 * Deprecated, not necessary to use this because the synchronization is handled directly in the method (decreases the scope of the synchronization)
 * Date: 2-10-17
 */
public class BlockingQueue {
	
	private Queue queue = new LinkedList(); 
	private int empty = 0; 
	private int MAX_TASK_IN_QUEUE = -1; 
	
	public BlockingQueue(int size) {
		this.MAX_TASK_IN_QUEUE = size; 
	}
	
	//taskes an object? because raw type...could make it take a task and make it a linkedList of a task 
	public synchronized void enqueue(Object task) throws InterruptedException {
		while(queue.size() == MAX_TASK_IN_QUEUE) {
			wait(); 
		}
		if (queue.size() == empty) {
			notifyAll(); 
		}
		
		queue.offer(task);
	}
	
	//returns Object? Check to see if this works to return a task 
	public synchronized Object dequeue() throws InterruptedException {
		while(queue.size() == empty) {
			wait(); 
		}
		if (queue.size() == MAX_TASK_IN_QUEUE) {
			notifyAll(); 
		}
		
		return queue.poll(); 
	}

}
