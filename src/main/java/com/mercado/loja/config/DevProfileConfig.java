package com.mercado.loja.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.mercado.loja.service.DBInitializeService;
import com.mercado.loja.service.EmailService;
import com.mercado.loja.service.SmtpMailService;

@Configuration
@Profile("dev")
public class DevProfileConfig {
	
	@Autowired
	private DBInitializeService db;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	
	@Bean
	public boolean initializateDB() throws ParseException {
		if (!"create".equals(strategy)) return false;
		db.init();
		System.out.println("CRIOU o bd");
		return true;
	}
	

	
	@Bean
	public EmailService getSmtpMailService() {
		return new SmtpMailService();
	}
}
