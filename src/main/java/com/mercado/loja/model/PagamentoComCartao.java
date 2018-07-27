package com.mercado.loja.model;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.mercado.loja.model.enums.EstadoPagamento;

@Entity
@JsonTypeName("PagamentoComCartao")
public class PagamentoComCartao extends Pagamento {
	private static final long serialVersionUID = 1L;
	
	private Integer numeroDeParcelas;

	public PagamentoComCartao() {
		super();
	}

	public PagamentoComCartao(Integer id, EstadoPagamento estadoPagamento, Pedido pedido, Integer numeroDeParcelas) {
		super(id, estadoPagamento, pedido);
		this.numeroDeParcelas = numeroDeParcelas;
	}

	public Integer getNumeroDeParcelas() {
		return numeroDeParcelas;
	}

	public void setNumeroDeParcelas(Integer numeroDeParcelas) {
		this.numeroDeParcelas = numeroDeParcelas;
	}
	
	
}
