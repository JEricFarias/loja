package com.mercado.loja.service;

import org.springframework.mail.SimpleMailMessage;

import com.mercado.loja.model.Pedido;

public interface EmailService {
	public void sendOrderConfimationEmail(Pedido pedido);
	
	public void sendEmail(SimpleMailMessage msg);
}
