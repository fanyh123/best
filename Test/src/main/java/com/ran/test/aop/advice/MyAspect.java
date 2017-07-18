package com.ran.test.aop.advice;

public class MyAspect {
	
	public boolean before(){
		System.out.println("MyAspect before notice......");
		return true;
	}
	
	public boolean around(){
		System.out.println("MyAspect around notice......");
		return true;
	}
	
	public boolean afterReturning(){
		System.out.println("MyAspect afterReturning notice......");
		return true;
	}
	
	public boolean afterThrowing(){
		System.out.println("MyAspect afterThrowing notice......");
		return true;
	}
	
	public boolean after(){
		System.out.println("MyAspect after notice......");
		return true;
	}	

}
