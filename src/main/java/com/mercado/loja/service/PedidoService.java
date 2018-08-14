package com.mercado.loja.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mercado.loja.model.Cliente;
import com.mercado.loja.model.ItemPedido;
import com.mercado.loja.model.PagamentoComBoleto;
import com.mercado.loja.model.Pedido;
import com.mercado.loja.model.enums.EstadoPagamento;
import com.mercado.loja.repository.ItemPedidoRepository;
import com.mercado.loja.repository.PagamentoRepository;
import com.mercado.loja.repository.PedidoRepository;
import com.mercado.loja.security.UserSS;
import com.mercado.loja.service.exception.AuthorizationException;
import com.mercado.loja.service.exception.ObjectNotFoundException;

@Service
public class PedidoService {
	@Autowired
	private PedidoRepository repo;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private BoletoService boletoCervice;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRepository itemPredidoRepository;
	
	@Autowired
	private ClienteService clienteService;	
	
	@Autowired
	private EmailService emailService;

	public Pedido find(Integer id) {
		Optional<Pedido> pedido = repo.findById(id);
		return pedido.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", type: " + Pedido.class.getName()));
	}

	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoCervice.preencerPagamentoComBoleto(pagto, obj.getInstante());
		}
		repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for(ItemPedido i : obj.getItens()) {
			i.setDesconto(0.0);
			i.setProduto(produtoService.find(i.getProduto().getId()));
			i.setPreco(i.getProduto().getPreco());
			i.setPedido(obj);
		}
		itemPredidoRepository.saveAll(obj.getItens());
		emailService.sendOrderConfimationHtmlEmail(obj); 
		return obj;
	}
	
	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		UserSS user = UserService.authenticated();
		if(user == null) {
			throw new AuthorizationException("Acesso a Pedidos negado!");
		}
		PageRequest infoPage = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente = clienteService.find(user.getId());
		return repo.findByCliente(cliente, infoPage);
	}
}
