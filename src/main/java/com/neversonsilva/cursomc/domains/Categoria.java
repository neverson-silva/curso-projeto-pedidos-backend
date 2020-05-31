package com.neversonsilva.cursomc.domains;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@Entity
public class Categoria implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	@Column
	@NonNull
	private Integer id;
	
	@Column
	@NonNull
	private String nome;
	
	@ManyToMany(mappedBy="categorias", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Produto> produtos = new ArrayList<>();
	
	public Categoria(Integer id, String nome) {
		this.id = id;
		this.nome = nome;
	}
}
