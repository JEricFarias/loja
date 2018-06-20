package com.mercado.loja.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercado.loja.model.Produto;
import com.mercado.loja.repository.ProdutoRepository;
import com.mercado.loja.service.exception.ObjectNotFoundException;

@Service
public class ProdutoService {
	@Autowired
	private ProdutoRepository repo;
	
	public Produto find(int id) {
		Optional<Produto> produto = repo.findById(id);
		return produto.orElseThrow(()-> new ObjectNotFoundException(
				"Porduto não encontrado! id: " + id
				+", tipo: " + Produto.class.getName()));
	}
}
