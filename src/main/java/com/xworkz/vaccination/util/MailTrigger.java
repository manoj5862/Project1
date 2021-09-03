package com.xworkz.vaccination.util;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailTrigger {
	
	private static Logger log;

	@Autowired
	private JavaMailSender mailSender;

	public MailTrigger() {
		log = Logger.getLogger(getClass());
		log.debug("Created" + this.getClass().getSimpleName());
	}

	public boolean mailTrigger(String email, String subject, String text) {
		log.info("Invoked mailTrigger");
		try {
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo(email);
			mailMessage.setSubject(subject);
			mailMessage.setText(text);
			this.mailSender.send(mailMessage);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			log.fatal("You have an exception "+e.getMessage());
			return false;
		}
		
		
	}
}
