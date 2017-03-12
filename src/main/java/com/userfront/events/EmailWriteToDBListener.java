package com.userfront.events;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import com.userfront.util.EmailSender;

@Component
public class EmailWriteToDBListener {

	
	@Autowired
	private EmailSender emailService;
	
	@EventListener
	public void onApplicationEvent(AccountOperationEvent event) {
		emailService.persistToDB(event.getEmail());
		
	}

	public EmailSender getEmailService() {
		return emailService;
	}

	public void setEmailService(EmailSender emailService) {
		this.emailService = emailService;
	}
}
