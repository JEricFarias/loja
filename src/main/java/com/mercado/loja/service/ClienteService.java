package com.mercado.loja.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.mercado.loja.dto.ClienteDTO;
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

	public void update(Cliente cliente) {
		find(cliente.getId());
		repo.save(cliente);
	}
	
	public void delete(Integer id) {
		repo.deleteById(id);
	}
	
	public List<Cliente> findAll() {
		return repo.findAll();
	}

	public Page<Cliente> findAllPages(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageInfo = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageInfo);
	}
	
	public Cliente fromDTO(ClienteDTO clienteNovo) {
		Cliente clienteAtual = find(clienteNovo.getId());
		return updateData(clienteAtual, clienteNovo);
	}
	
	// Método de serviço que atualiza o cliente com as novas informações.
	// O Objeto atualizado não é persistido.
	private Cliente updateData(Cliente atual, ClienteDTO novo) {
		atual.setEmail(novo.getEmail());
		atual.setNome(novo.getNome());
		return atual;
	}
}
