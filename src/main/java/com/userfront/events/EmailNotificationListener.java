package com.userfront.events;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.userfront.util.EmailSender;

@Component
public class EmailNotificationListener {

	@Autowired
	private EmailSender emailService;

	@EventListener
	public void onApplicationEvent(AccountOperationEvent event) {
		emailService.sentEmail(event.getEmail());
		
	}
	
}
