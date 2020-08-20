package com.neversonsilva.cursomc.dto;

import com.neversonsilva.cursomc.domains.Produto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	

	private String nome;
	
	private Double preco;

	public ProdutoDTO(Produto produto) {
		this.id = produto.getId();
		this.preco = produto.getPreco();
		this.nome = produto.getNome();
	}
    
}