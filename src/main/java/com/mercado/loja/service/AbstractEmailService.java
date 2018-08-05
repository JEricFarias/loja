package com.mercado.loja.service;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.mercado.loja.model.Pedido;

public abstract class AbstractEmailService implements EmailService{
	@Value("${email.default.sender}")
	private String remetente; 
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private JavaMailSender jms;

	@Override
	public void sendOrderConfimationEmail(Pedido pedido) {
		SimpleMailMessage email = prepareSimpleMailMessageFromPedido(pedido);
		sendEmail(email);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido pedido) {
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(pedido.getCliente().getEmail());
		email.setFrom(remetente);
		email.setSubject("Pedido confirmado! Números do pedido: " + pedido.getId());
		email.setSentDate(new Date(System.currentTimeMillis()));
		email.setText(pedido.toString());
		return email;
	}
	
	@Override
	public void sendOrderConfimationHtmlEmail(Pedido pedido) {
		try {
			MimeMessage email = prepareMimeMessageFromPedido(pedido);
			sendHtmlEmail(email);
		} catch (MessagingException e) {
			sendOrderConfimationEmail(pedido);
		}
	}
	
	protected MimeMessage prepareMimeMessageFromPedido(Pedido pedido) throws MessagingException {
		MimeMessage mm = jms.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mm, true);
		mmh.setTo(pedido.getCliente().getEmail());
		mmh.setFrom(remetente);
		mmh.setSubject("Pedido confirmado! Código: " + pedido.getId());
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		mmh.setText(htmlFromTemplatePedido(pedido), true);
		return mm;
	}

	protected String htmlFromTemplatePedido(Pedido obj) {
		Context context = new Context();
		context.setVariable("pedido", obj);
		return templateEngine.process("email/confirmacaoPedido", context);
	}
}
