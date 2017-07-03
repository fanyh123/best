package com.ran.test.thread;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class TestPostConstruct {
	
	@PostConstruct
	public void init(){		
		System.out.println("TestPostConstruct init......");
	}

}
