package com.userfront.util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import com.userfront.dao.EmailDao;
import com.userfront.domain.Email;
import com.userfront.domain.User;
import com.userfront.events.OnRegistrationCompleteEvent;

@Component
public class EmailSender {

	private Logger logger = LoggerFactory.getLogger(EmailSender.class);
	
	@Autowired
	private EmailDao emailDao;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private Environment env;

    @Autowired
    private MessageSource messages;
	
//	@EventListener
	public void sentEmail(Email email) {
		mailSender.send(constructEmailMEssage(email));
	}
	
	private SimpleMailMessage constructEmailMEssage(final Email email) {
        final SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email.getTo());
        simpleMailMessage.setSubject("Status account");
        simpleMailMessage.setText(email.getSubject());
        simpleMailMessage.setFrom(env.getProperty("support.email"));
        
        return simpleMailMessage;
	}
	
	public void persistToDB(Email email) {
		emailDao.save(email);
	}
}
