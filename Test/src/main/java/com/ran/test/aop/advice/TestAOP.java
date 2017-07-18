package com.ran.test.aop.advice;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ran.test.aop.advice.service.BankService;

public class TestAOP {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-aop.xml");
        BankService bankService = context.getBean("bankService", BankService.class);
        boolean res = bankService.transfer("张三", "李四", 200);
        System.out.println("bank transfer resulte = " + res);

	}

}
