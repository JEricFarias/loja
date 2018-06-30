package com.mercado.loja.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.mercado.loja.model.Categoria;
import com.mercado.loja.repository.CategoriaRepository;
import com.mercado.loja.service.exception.IntegrityViolationException;
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
	
	public Categoria update(Categoria categoria) {
		find(categoria.getId());
		return repo.save(categoria);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new IntegrityViolationException(
					"Uma categoria que possui produtos não pode ser deletada. Id: "
					+ id + ", type: " + Categoria.class.getName());
		}
	}

	public List<Categoria> findAll() {
		return repo.findAll();
	}
	
//	public Page<Categoria> findAllPages(Integer page, Integer linesPerPage, String orderBy, String direction){
//		PageRequest paginas = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
//		return repo.findAll(paginas);
//	}
	
	public Page<Categoria> findAllPages(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageInfo = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageInfo);
	}
}
