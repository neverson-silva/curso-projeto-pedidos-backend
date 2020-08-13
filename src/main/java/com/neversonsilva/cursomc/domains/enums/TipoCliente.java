package com.neversonsilva.cursomc.domains.enums;
import lombok.Getter;

public enum TipoCliente {
	
	PESSOA_FISICA(1, "Pessoa Física"),
	PESSOA_JURIDICA(2, "Pessoa Jurídica");
	
	@Getter
	private int code;
	
	@Getter
	private String descricao;
	
	private TipoCliente(int code, String descricao) {
		this.code = code;
		this.descricao = descricao;
	}
	
	public static TipoCliente toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}
		for (TipoCliente x: TipoCliente.values()) {
			if (cod.equals(x.getCode())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Id inválido: " + cod);
	}
	
	
	
}
