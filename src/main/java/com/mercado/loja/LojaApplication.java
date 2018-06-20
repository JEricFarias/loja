package com.mercado.loja;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mercado.loja.model.Categoria;
import com.mercado.loja.model.Cidade;
import com.mercado.loja.model.Estado;
import com.mercado.loja.model.Produto;
import com.mercado.loja.repository.CategoriaRepository;
import com.mercado.loja.repository.CidadeRepository;
import com.mercado.loja.repository.EstadoRepository;
import com.mercado.loja.repository.ProdutoRepository;

@SpringBootApplication
public class LojaApplication implements CommandLineRunner{

	@Autowired
	private CategoriaRepository repoCat;
	
	@Autowired
	private ProdutoRepository repoProd;
	
	@Autowired
	private EstadoRepository estadoRepo;
	
	@Autowired
	private CidadeRepository cidadeRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(LojaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		repoCat.saveAll(Arrays.asList(cat1, cat2));
		repoProd.saveAll(Arrays.asList(p1,p2,p3));
		repoCat.flush();
		repoProd.flush();
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().add(p2);
		
		p1.getCategorias().add(cat1);
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().add(cat1);
		repoCat.saveAll(Arrays.asList(cat1, cat2));
		repoProd.saveAll(Arrays.asList(p1,p2,p3));
		
		
		/*Cidade e Estado*/
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.setCidades(Arrays.asList(c1));
		est2.setCidades(Arrays.asList(c2, c3));
		
		estadoRepo.saveAll(Arrays.asList(est1, est2));
		cidadeRepo.saveAll(Arrays.asList(c1,c2,c3));		
		
	}
}
