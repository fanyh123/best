package com.ran.test.scheduler;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class TestPostConstruct {
	
	//初始化springmvc时，先走构造器初始化，后走@PostConstruct
	
	public TestPostConstruct(){
		System.out.println("~~~~~~TestPostConstruct......");
	}
	
	@PostConstruct
	public void init(){		
		System.out.println("TestPostConstruct init......");
	}

}
