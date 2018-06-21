package com.mercado.loja.model.enums;

public enum TipoCliente {
	PESSOAFISICA(1, "Pessoa Física"),
	PESSOAJURIDICA(2, "Pessoa Juridica");
	
	private Integer codigo;
	private String descricao;
	
	private TipoCliente(Integer code, String descricao) {
		this.codigo = code;
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public static TipoCliente toEnum(Integer code) {
		if(code == null) return null;
		
		for(TipoCliente x: TipoCliente.values()) {
			if(code.equals(x.getCodigo())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Não ha tipo de cliente para o codigo: " + code);
	}
	

}
