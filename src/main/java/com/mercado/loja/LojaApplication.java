package com.mercado.loja;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mercado.loja.model.Categoria;
import com.mercado.loja.model.Cidade;
import com.mercado.loja.model.Cliente;
import com.mercado.loja.model.Endereco;
import com.mercado.loja.model.Estado;
import com.mercado.loja.model.ItemPedido;
import com.mercado.loja.model.PagamenteComBoleto;
import com.mercado.loja.model.PagamentoComCartao;
import com.mercado.loja.model.Pedido;
import com.mercado.loja.model.Produto;
import com.mercado.loja.model.enums.EstadoPagamento;
import com.mercado.loja.model.enums.TipoCliente;
import com.mercado.loja.repository.CategoriaRepository;
import com.mercado.loja.repository.CidadeRepository;
import com.mercado.loja.repository.ClienteRepository;
import com.mercado.loja.repository.EnderecoRepository;
import com.mercado.loja.repository.EstadoRepository;
import com.mercado.loja.repository.ItemPedidoRepository;
import com.mercado.loja.repository.PagamentoRepository;
import com.mercado.loja.repository.PedidoRepository;
import com.mercado.loja.repository.ProdutoRepository;

@SpringBootApplication
public class LojaApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository repoCat;

	@Autowired
	private ProdutoRepository repoProd;

	@Autowired
	private EstadoRepository estadoRepo;

	@Autowired
	private CidadeRepository cidadeRepo;

	@Autowired
	private ClienteRepository clienteRepo;

	@Autowired
	private EnderecoRepository enderecoRepo;
	
	@Autowired
	private PedidoRepository pedidoRepo;
	
	@Autowired
	private PagamentoRepository pagamentoRepo;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepo;

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
		repoProd.saveAll(Arrays.asList(p1, p2, p3));
		repoCat.flush();
		repoProd.flush();

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().add(p2);

		p1.getCategorias().add(cat1);
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().add(cat1);
		repoCat.saveAll(Arrays.asList(cat1, cat2));
		repoProd.saveAll(Arrays.asList(p1, p2, p3));

		/* Cidade e Estado */
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);

		est1.setCidades(Arrays.asList(c1));
		est2.setCidades(Arrays.asList(c2, c3));

		estadoRepo.saveAll(Arrays.asList(est1, est2));
		cidadeRepo.saveAll(Arrays.asList(c1, c2, c3));

		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));

		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 203", "Jardim", "38220834", cidadeRepo.getOne(1),
				cli1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "sala 800", "Centro", "38777012", cidadeRepo.getOne(1),
				cli1);

		clienteRepo.save(cli1);
		enderecoRepo.saveAll(Arrays.asList(e1, e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), e1, cli1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), e2, cli1);
		
		SimpleDateFormat sdfData = new SimpleDateFormat("dd/MM/yyyy");
		PagamentoComCartao pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		PagamenteComBoleto pagto2 = new PagamenteComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdfData.parse("20/10/2017"), null);
		ped2.setPagamento(pagto2);
		cli1.setPedidos(Arrays.asList(ped1, ped2));
		
		pedidoRepo.saveAll(Arrays.asList(ped1,ped2));
		pagamentoRepo.saveAll(Arrays.asList(pagto1, pagto2));

		ItemPedido ip1 = new ItemPedido(ped1, p1, 200.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().add(ip3);
		
		p1.getItens().add(ip1);
		p2.getItens().add(ip3);
		p3.getItens().add(ip2);
		
		itemPedidoRepo.saveAll(Arrays.asList(ip1, ip2, ip3));
	}
}
