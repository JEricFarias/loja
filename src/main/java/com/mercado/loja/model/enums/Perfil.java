package com.mercado.loja.model.enums;

public enum Perfil {
	CLIENTE(1, "RULE_CLIENTE"),
	ADMIN(2, "RULE_ADMIN");
	
	private Integer cod;
	private String descricao;
	
	private Perfil(Integer cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public Integer getCodigo() {
		return this.cod;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
	public static Perfil toEnum(Integer cod) {
		if(cod == null) return null;
		
		for(Perfil x : Perfil.values()) {
			if(cod == x.getCodigo()) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("O Código informado não pertence a um Perfil válido");
	}
}
