package com.mercado.loja.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mercado.loja.dto.ClienteDTO;
import com.mercado.loja.dto.ClienteNewDTO;
import com.mercado.loja.model.Cidade;
import com.mercado.loja.model.Cliente;
import com.mercado.loja.model.Endereco;
import com.mercado.loja.model.enums.TipoCliente;
import com.mercado.loja.repository.ClienteRepository;
import com.mercado.loja.repository.EnderecoRepository;
import com.mercado.loja.service.exception.ObjectNotFoundException;

@Service
public class ClienteService {
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Cliente find(Integer id) {
		Optional<Cliente> cliente = repo.findById(id);
		return cliente.orElseThrow(()-> new ObjectNotFoundException(
				"Objeto não encontrado! id: " + id
				+ ", type: " + Cliente.class.getName()));
	}

	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		Cliente cliente = repo.save(obj);
		enderecoRepository.saveAll(cliente.getEnderecos());
		return cliente;
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
	
	public Cliente fromDTO(ClienteNewDTO objDTO) {
		Cliente cli = new Cliente(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfOuCnpj(), TipoCliente.toEnum(objDTO.getTipoCliente()));
		Cidade cid = new Cidade(objDTO.getCidade(), null, null);
		Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplemento(), objDTO.getBairro(), objDTO.getCep(), cid, cli);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDTO.getTelefone1());
		if(objDTO.getTelefone2() != null) {
			cli.getTelefones().add(objDTO.getTelefone2());
		}
		if(objDTO.getTelefone3() != null) {
			cli.getTelefones().add(objDTO.getTelefone3());
		}
		return cli;
	}
	
	// Método de serviço que atualiza o cliente com as novas informações.
	// O Objeto atualizado não é persistido.
	private Cliente updateData(Cliente atual, ClienteDTO novo) {
		atual.setEmail(novo.getEmail());
		atual.setNome(novo.getNome());
		return atual;
	}
}
