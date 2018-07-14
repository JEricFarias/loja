package com.mercado.loja.resource;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mercado.loja.dto.ClienteDTO;
import com.mercado.loja.dto.ClienteNewDTO;
import com.mercado.loja.model.Cliente;
import com.mercado.loja.service.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService service;
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Cliente> buscar(@PathVariable Integer id){
		return ResponseEntity.ok().body(service.find(id));
	}
	
	@PostMapping
	public ResponseEntity<Void> inserir(@RequestBody ClienteNewDTO clienteNovo){
		Cliente cliente = service.insert(service.fromDTO(clienteNovo));
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cliente.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<Void> atualizar(@Valid @RequestBody ClienteDTO cliente, @PathVariable Integer id){
		cliente.setId(id);
		Cliente atualizado = service.fromDTO(cliente);
		service.update(atualizado);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<?> deletar(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping
	public ResponseEntity<List<ClienteDTO>> buscarTodos(){
		List<ClienteDTO> list = service.findAll().stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/page")
	public ResponseEntity<Page<ClienteDTO>> buscarPaginas(
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "lines-per-page", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value = "order-by", defaultValue = "nome") String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC") String direction){
		 Page<Cliente> pages = service.findAllPages(page, linesPerPage, orderBy, direction);
		 Page<ClienteDTO> pagesInfo = pages.map(obj -> new ClienteDTO(obj));
		 return ResponseEntity.ok().body(pagesInfo);
	}
}
