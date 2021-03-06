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

import com.mercado.loja.model.Cliente;
import com.mercado.loja.model.Pedido;

public abstract class AbstractEmailService implements EmailService{
	@Value("${email.default.sender}")
	private String remetente; 
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired(required=false)
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
	
	@Override
	public void sendNewPasswordEmail(Cliente cliente, String newpass) {
		SimpleMailMessage email = prepareNewPasswordEmail(cliente, newpass);
		sendEmail(email);
	}

	protected SimpleMailMessage prepareNewPasswordEmail(Cliente cliente, String newpass) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setSubject("Nova senha!");
		sm.setTo(cliente.getEmail());
		sm.setFrom(remetente);
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText("Nova senha " + newpass);
		return sm; 
	}
}
