package com.neversonsilva.cursomc.domains;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.neversonsilva.cursomc.domains.enums.TipoCliente;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity
@EqualsAndHashCode(of= {"id"})
@ToString
@NoArgsConstructor
public class Cliente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private Integer id;
	
	@NonNull
	@Getter
	@Setter
	private String nome;
	
	@NonNull
	@Getter
	@Setter
	private String email;

	@NonNull
	@Getter
	@Setter
	private String cpfOuCnpj;

	@NonNull
	private Integer tipo;

	@Getter
	@Setter
	@OneToMany(mappedBy="cliente")
	@JsonManagedReference
	private List<Endereco> enderecos = new ArrayList<>();
	
	//entidade fraca
	@Getter
	@Setter
	@ElementCollection
	@CollectionTable(name="telefone")	
	private Set<String> telefones = new HashSet<>();
	
	@Getter
	@Setter
	@OneToMany(mappedBy="cliente")
	@JsonManagedReference
	private List<Pedido> pedidos = new ArrayList<>();
	
	public Cliente (Integer id, String nome, String email, String cpfOuCnpj, TipoCliente tipo) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.cpfOuCnpj = cpfOuCnpj;
		this.tipo = tipo.getCod();
		
	}
	
	public Cliente (String nome, String email, String cpfOuCnpj, TipoCliente tipo) {
		super();
		this.nome = nome;
		this.email = email;
		this.cpfOuCnpj = cpfOuCnpj;
		this.tipo = tipo.getCod();
		
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(TipoCliente tipo) {
		this.tipo = tipo.getCod();
	}	
	
}
