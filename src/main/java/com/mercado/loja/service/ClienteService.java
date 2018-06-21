package com.mercado.loja.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercado.loja.model.Cliente;
import com.mercado.loja.repository.ClienteRepository;
import com.mercado.loja.service.exception.ObjectNotFoundException;

@Service
public class ClienteService {
	@Autowired
	private ClienteRepository repo;
	
	public Cliente find(Integer id) {
		Optional<Cliente> cliente = repo.findById(id);
		return cliente.orElseThrow(()-> new ObjectNotFoundException(
				"Objeto não encontrado! id: " + id
				+ ", type: " + Cliente.class.getName()));
	}
}
