package com.ran.test.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component("testScheduler")
public class Test {
	
	@Scheduled(cron="*/5 * * * * ?")
	public void shcedulerTest(){
		System.out.println("shcedulerTest------");
	}

}
