package com.mercado.loja.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mercado.loja.model.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {

}
