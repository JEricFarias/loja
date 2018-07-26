package com.mercado.loja.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mercado.loja.dto.ProdutoDTO;
import com.mercado.loja.model.Produto;
import com.mercado.loja.resource.util.URL;
import com.mercado.loja.service.ProdutoService;

@RestController
@RequestMapping(value="produtos")
public class ProdutoResource {

	@Autowired
	private ProdutoService service;
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Produto> buscar(@PathVariable int id){
		return ResponseEntity.ok().body(service.find(id));
	}
	
	@GetMapping
	public ResponseEntity<Page<ProdutoDTO>> buscarPorCategoriaFragmentoDeNome(
			@RequestParam(name = "nome", defaultValue = "") String nome,
			@RequestParam(name = "categorias", defaultValue = "") String categorias,
			@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "lines-per-page", defaultValue = "24") Integer linesPerPage,
			@RequestParam(name = "order-by", defaultValue = "nome") String orderBy,
			@RequestParam(name = "direction", defaultValue = "ASC") String direction){
		
		String searchForThename = URL.decodeParam(nome);
		List<Integer> ids = URL.decodeIntList(categorias);
		Page<Produto> produtos = service.buscarPorCategoriaFragmentoDeNome(searchForThename, ids, page, linesPerPage, orderBy, direction);
		Page<ProdutoDTO> produtosDTO = produtos.map(x->new ProdutoDTO(x));
		return ResponseEntity.ok().body(produtosDTO);
	}
}
