package com.mercado.loja.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mercado.loja.model.Categoria;
import com.mercado.loja.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
	@Transactional(readOnly=true)
	@Query("SELECT p FROM Produto p INNER JOIN p.categorias cat WHERE p.nome LIKE %:nome% AND cat IN :categorias")
	Page<Produto> BuscaPorCategoriasFragmentosDeNome(@Param("nome") String nome, @Param("categorias") List<Categoria> categorias, Pageable pageRequest);
}
