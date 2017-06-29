package com.ran.test.thread;

public class ThreadOne extends Thread {
	
	private String area = "cn";
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("ThreadName="+Thread.currentThread().getName());
		System.out.println("area="+area);
	}

}
 