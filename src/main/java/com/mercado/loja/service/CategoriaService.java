package com.mercado.loja.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercado.loja.model.Categoria;
import com.mercado.loja.repository.CategoriaRepository;

@Service
public class CategoriaService {
	@Autowired
	private CategoriaRepository repo;
	
	public Categoria find(int id) {
		Optional<Categoria> categoria = repo.findById(id);
		return categoria.orElse(null);
	}

}
