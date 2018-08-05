package com.mercado.loja.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class SmtpMailService extends AbstractEmailService {

	@Autowired
	private MailSender mailSender;
	
	public static final Logger LOG = LoggerFactory.getLogger(SmtpMailService.class);
	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LOG.info("Enviando Email...");
		mailSender.send(msg);
		LOG.info("Email Enviado!");

	}

}
