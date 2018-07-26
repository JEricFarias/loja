package com.mercado.loja.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.mercado.loja.model.Categoria;
import com.mercado.loja.model.Produto;
import com.mercado.loja.repository.CategoriaRepository;
import com.mercado.loja.repository.ProdutoRepository;
import com.mercado.loja.service.exception.ObjectNotFoundException;

@Service
public class ProdutoService {
	@Autowired
	private ProdutoRepository repo;
	
	@Autowired
	private CategoriaRepository categoriasRepository;
	
	public Produto find(int id) {
		Optional<Produto> produto = repo.findById(id);
		return produto.orElseThrow(()-> new ObjectNotFoundException(
				"Porduto não encontrado! id: " + id
				+", tipo: " + Produto.class.getName()));
	}
	
	public Page<Produto> buscarPorCategoriaFragmentoDeNome(String nome, List<Integer> categorias, Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageInfo = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categoriasManaged = categoriasRepository.findAllById(categorias);
		return repo.BuscaPorCategoriasFragmentosDeNome(nome, categoriasManaged, pageInfo);
	}
}
