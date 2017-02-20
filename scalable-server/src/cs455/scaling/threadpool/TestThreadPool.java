package cs455.scaling.threadpool;

import cs455.scaling.task.TestTask;

public class TestThreadPool {

	
	public static void main(String args[]) throws InterruptedException {
		//create the threadPool with:
		//queueSize = 3
		//numberThreads = 4
		ThreadPool threadPool = new ThreadPool(3,4);
		for (int taskNumber = 1; taskNumber <= 7; taskNumber++) {
			TestTask task = new TestTask(taskNumber); 
			threadPool.submitTask(task);
		}
	}
}
