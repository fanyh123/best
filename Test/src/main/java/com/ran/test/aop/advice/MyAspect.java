package com.ran.test.aop.advice;

import org.aspectj.lang.ProceedingJoinPoint;

public class MyAspect {
	
	public boolean before(){
		System.out.println("MyAspect before notice......");
		return true;
	}
	
	public boolean around(ProceedingJoinPoint pJoinPoint ){
		System.out.println("MyAspect around notice......");
		try {
			Object res = pJoinPoint.proceed();
			System.out.println("around res = " + res);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
