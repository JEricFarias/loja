package com.mercado.loja.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.mercado.loja.service.DBInitializeService;

@Configuration
@Profile("test")
public class DBInitialize {
	@Autowired
	DBInitializeService bd;
	
	@Bean
	public boolean inicializarBancoDeTeste() throws ParseException {
		bd.init();
		return true;
	}
}
