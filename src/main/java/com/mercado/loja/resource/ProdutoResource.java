package com.mercado.loja.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercado.loja.model.Produto;
import com.mercado.loja.service.ProdutoService;

@RestController
@RequestMapping(value="produtos")
public class ProdutoResource {

	@Autowired
	private ProdutoService service;
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Produto> getProduto(@PathVariable int id){
		return ResponseEntity.ok().body(service.find(id));
	}
}
