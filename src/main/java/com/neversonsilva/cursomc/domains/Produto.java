package com.neversonsilva.cursomc.domains;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@Entity
public class Produto {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	@Column
	@NonNull
	private Integer id;
	
	@Column
	@NonNull
	private String nome;
	
	@Column
	@NonNull
	private Double preco;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name="PRODUTO_CATEGORIA", 
		joinColumns=@JoinColumn(name="produto_id"),
		inverseJoinColumns= @JoinColumn(name="categoria_id"))
    @JsonBackReference
	private List<Categoria> categorias = new ArrayList<>();
	
	public Produto(Integer id, String nome, Double preco) {
		this.id = id;
		this.nome = nome;
		this.preco = preco;
	}
}
