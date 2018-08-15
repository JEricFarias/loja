package com.mercado.loja.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mercado.loja.model.Cliente;
import com.mercado.loja.repository.ClienteRepository;
import com.mercado.loja.service.exception.ObjectNotFoundException;

@Service
public class AuthService {
	
	@Autowired
	private ClienteRepository clienteRepo;
	
	@Autowired
	private BCryptPasswordEncoder cbEncode;
	
	@Autowired
	private EmailService emailService;
	
	public void sendNewPassword(String email) {
		Cliente cliente = clienteRepo.findByEmail(email);
		
		if(cliente == null) {
			throw new ObjectNotFoundException("Email " + email + " não encontrado!");
		}
		
		String newpass = newPassword();
		cliente.setSenha(cbEncode.encode(newpass));
		clienteRepo.save(cliente);
		emailService.sendNewPasswordEmail(cliente, newpass);
	}

	private String newPassword() {
		char[] vet = new char[10];
		for(int i = 0; i < vet.length; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		Random rand = new Random();
		int opt = rand.nextInt(3);
		if(opt == 0) { // gera um digito
			return (char) (rand.nextInt(10) + 48);
		}else if(opt == 1) { // gera letra maiuscula 
			return (char) (rand.nextInt(26) + 65);
		}else { // gera letra minuscula
			return (char) (rand.nextInt(26) + 97);
		}
	}
}
