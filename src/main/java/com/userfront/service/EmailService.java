package com.userfront.service;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.userfront.domain.Email;
import com.userfront.events.AccountOperationEvent;

@Service
public class EmailService implements ApplicationContextAware{

	
	private BlockingQueue<Email> queue;
	private ApplicationContext context;
	
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	
    private List<Thread> workers ; 
	
	@Autowired
	public EmailService(BlockingQueue<Email> queue) {
		this.queue = queue;
		this.workers = new ArrayList<>();
		for(int i = 0 ; i < 3; i++) {
			workers.add(new Thread(new Worker()));
		}
/*		taskExecutor.execute(new Runnable() {
			
			@Override
			public void run() {
				try {
					while(true) {
						registerEmail(queue.take());
					
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		});*/
	}
	
	@PostConstruct
	public void initIt() throws Exception {
		
		if(workers != null) {
			for(Thread thread : workers) {
				taskExecutor.execute(new Worker());
			}
		}
	}
	
	@Scope("prototype")
	class Worker implements Runnable{

		@Override
		public void run() {
			try {
				while(true) {
					registerEmail(queue.take());
				
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

	private void registerEmail(Email email) {
		context.publishEvent(new AccountOperationEvent(email));
		
	}
	public void sendNotification(Email email){
		try {
			queue.put(email);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		this.context = arg0;
	}
	
}
