package com.neversonsilva.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import com.neversonsilva.cursomc.domains.Categoria;

import lombok.NoArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
public class CategoriaDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@NotEmpty(message= "Preenchimento Obrigatório")
	@Length(min = 5, max = 80, message = "O tamanho deve ser entre 5 e 80 caracteres")
	private String nome;
	
	public CategoriaDTO(Categoria categoria) {
		this.id = categoria.getId();
		this.nome = categoria.getNome();
	}
}
