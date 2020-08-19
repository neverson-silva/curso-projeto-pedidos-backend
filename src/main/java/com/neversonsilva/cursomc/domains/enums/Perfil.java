package com.neversonsilva.cursomc.domains.enums;

import lombok.Getter;

public enum Perfil {

	ADMIN(1, "ROLE_ADMIN"),
	CLIENTE(2, "ROLE_CLIENTE");
	
	@Getter
	private int code;
	
	@Getter
	private String descricao;
	
	private Perfil(int code, String descricao) {
		this.code = code;
		this.descricao = descricao;
	}
	
	public static Perfil toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}
		for (Perfil x: Perfil.values()) {
			if (cod.equals(x.getCode())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Id inválido: " + cod);
	}
	
	
}
