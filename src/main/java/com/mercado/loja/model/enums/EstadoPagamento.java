package com.mercado.loja.model.enums;

public enum EstadoPagamento {
	PENDENTE(1, "Pendente"),
	QUITADO(2, "Quitado"),
	CANCELADO(3, "Cancelado");
	
	private Integer codigo;
	private String descricao;
	
	private EstadoPagamento(Integer code, String descricao) {
		this.codigo = code;
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static EstadoPagamento toEnum(Integer code) {
		if(code == null) return null;
		
		for(EstadoPagamento x: EstadoPagamento.values()) {
			if(x.getCodigo() == code) return x;
		}
		
		throw new IllegalArgumentException("O Código informado não pertence a um EstadoPagamento válido");
	}
}
