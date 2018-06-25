package com.mercado.loja.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercado.loja.model.Categoria;
import com.mercado.loja.repository.CategoriaRepository;
import com.mercado.loja.service.exception.ObjectNotFoundException;

@Service
public class CategoriaService {
	@Autowired
	private CategoriaRepository repo;
	
	public Categoria find(Integer id) {
		Optional<Categoria> categoria = repo.findById(id);
		return categoria.orElseThrow(()-> new ObjectNotFoundException(
				"Objeto não encontrado! id: " 
				+ id
				+ ", Tipo: " + Categoria.class.getName()));
	}
	
	public Categoria insert(Categoria categoria) {
		categoria.setId(null); // apenas para ter serteza que o objeto é novo
		return repo.save(categoria);
	}
}
