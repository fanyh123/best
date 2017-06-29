package com.ran.test.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestThread {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*Executor接口，只有一个方法void execute(Runnable command)，用来执行线程的工具。
		 *ExecutorService线程池接口，实现类：ThreadPoolExecutor
		 *ScheduledExecutorService定时启动的线程池接口，ScheduledThreadPoolExecutor实现类
		 *Executors提供生成线程池的静态方法构造器
		 *
		 *
		 * 线程、线程池:处理队列中的任务
		 * 任务队列:BlockingQueue、LinkedBlockingQueue、LinkedList<Runnable> workQueue实现队列
		 * Queue的特点：FIFO，队列阻塞
		 * LinkedList实现了Queue接口
		 * JUC,减少synchronized会造成的阻塞和死锁。
		 */
		ThreadOne threadOne = new ThreadOne();
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		executorService.execute(threadOne);
		
		//队列和线程池
		BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();
		for(int i=0;i<100;i++){
			workQueue.add(new ThreadOne());
		}		
		ThreadPoolExecutor pool = new ThreadPoolExecutor(5,10,10,TimeUnit.SECONDS,workQueue);
		
		System.out.println("poolSize="+pool.getPoolSize());
		//int corePoolSize,int maximumPoolSize,long keepAliveTime(空闲等待时间),TimeUnit unit(前一参数的单位),BlockingQueue<Runnable> workQueue

	}

}
