package com.mercado.loja.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercado.loja.model.Produto;
import com.mercado.loja.repository.ProdutoRepository;

@Service
public class ProdutoService {
	@Autowired
	private ProdutoRepository repo;
	
	public Produto find(int id) {
		Optional<Produto> prod = repo.findById(id);
		return prod.orElse(null);
	}
}
