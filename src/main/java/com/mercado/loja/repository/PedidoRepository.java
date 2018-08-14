package com.mercado.loja.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mercado.loja.model.Cliente;
import com.mercado.loja.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer>{
	// Padrão de nomes de metodos
	@Transactional(readOnly = true)
	Page<Pedido> findByCliente(Cliente cliete, Pageable pageRequest);
}
