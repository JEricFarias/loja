package com.mercado.loja.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.mercado.loja.model.Pedido;

public abstract class AbstractEmailService implements EmailService{
	@Value("${email.default.sender}")
	private String remetente; 

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
}
