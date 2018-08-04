package com.mercado.loja.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.mercado.loja.service.DBInitializeService;
import com.mercado.loja.service.EmailService;
import com.mercado.loja.service.MockMailService;

@Configuration
@Profile("test")
public class TestProfileConfig {
	@Autowired
	DBInitializeService bd;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	
	@Bean
	public boolean inicializarBancoDeTeste() throws ParseException {
		if (!"create".equals(strategy)) return false;
		bd.init();
		return true;
	}
	
	@Bean
	public EmailService getEmailService() {
		return new MockMailService();
	}
}
