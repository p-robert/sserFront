package com.userfront.events.listener;

import java.util.Locale;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.userfront.domain.User;
import com.userfront.events.OnRegistrationCompleteEvent;
import com.userfront.service.UserService;

@Component
public class RegistrationListener {
	private static final String EMAIL_SUBJECT = "Registration Confirmation";
	@Autowired
	private UserService userService;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private Environment env;

    @Autowired
    private MessageSource messages;
    
	@EventListener
	private void confirmRegistration(final OnRegistrationCompleteEvent event){
		final User user = event.getUser();
		final String token = UUID.randomUUID().toString();
		
		userService.createVerificationTokenForUser(user,token);
	
		final SimpleMailMessage email = constructEmailMEssage(event,user,token);
		mailSender.send(email);
	}

	private SimpleMailMessage constructEmailMEssage(final OnRegistrationCompleteEvent event,final User user,final String token) {
		final String recipientAddress = user.getEmail();
		final String confirmationUrl = event.getAppUrl() + "/registrationConfirm.html?token=" + token;
		final String message = messages.getMessage("message.regSucc", null, event.getLocale());
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(EMAIL_SUBJECT);
        email.setText(message + " \r\n" + confirmationUrl);
        email.setFrom(env.getProperty("support.email"));
        
        return email;
	}
}
