package com.mercado.loja.service;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SmtpMailService extends AbstractEmailService {

	@Autowired
	private MailSender mailSender;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public static final Logger LOG = LoggerFactory.getLogger(SmtpMailService.class);
	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LOG.info("Enviando Email...");
		mailSender.send(msg);
		LOG.info("Email Enviado!");

	}
	@Override
	public void sendHtmlEmail(MimeMessage msg) {
		LOG.info("Enviando Email...");
		javaMailSender.send(msg);
		LOG.info("Email enviado!");
		
	}

}
