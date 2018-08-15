package com.mercado.loja.service;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.mercado.loja.model.Cliente;
import com.mercado.loja.model.Pedido;

public interface EmailService {
	public void sendOrderConfimationEmail(Pedido pedido);
	
	public void sendEmail(SimpleMailMessage msg);
	
	public void sendOrderConfimationHtmlEmail(Pedido pedido);
	
	public void sendHtmlEmail(MimeMessage msg);

	public void sendNewPasswordEmail(Cliente cliente, String newpass);
}
