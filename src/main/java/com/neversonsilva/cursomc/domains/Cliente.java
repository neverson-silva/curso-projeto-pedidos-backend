package com.neversonsilva.cursomc.domains;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.neversonsilva.cursomc.domains.enums.Perfil;
import com.neversonsilva.cursomc.domains.enums.TipoCliente;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity
@EqualsAndHashCode(of= {"id"})
@ToString
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
	@Column(unique = true)
	private String email;

	@NonNull
	@Getter
	@Setter
	private String cpfOuCnpj;

	@NonNull
	private Integer tipo;

	@Getter
	@Setter
	@OneToMany(mappedBy="cliente", cascade = CascadeType.ALL)
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
	@JsonIgnore
	private List<Pedido> pedidos = new ArrayList<>();
	
		
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="perfis")	
	private Set<Integer> perfis = new HashSet<>();
	
	
	@Getter
	@Setter
	@JsonIgnore
	private String senha;
	
	public Cliente (Integer id, String nome, String email, String cpfOuCnpj, TipoCliente tipo, String senha) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.cpfOuCnpj = cpfOuCnpj;
		this.tipo = tipo == null ? null : tipo.getCode();
		this.senha = senha;
		addPerfil(Perfil.CLIENTE);
		
	}
	
	public Cliente (String nome, String email, String cpfOuCnpj, TipoCliente tipo, String senha) {
		super();
		this.nome = nome;
		this.email = email;
		this.cpfOuCnpj = cpfOuCnpj;
		this.tipo = tipo.getCode();
		this.senha = senha;	
		addPerfil(Perfil.CLIENTE);
	}

	@JsonIgnore
	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(TipoCliente tipo) {
		this.tipo = tipo.getCode();
	}	
	
	public Set<Perfil> getPerfils() {
		
		return perfis.stream()
					  .map(perfil -> Perfil.toEnum(perfil))
					  .collect(Collectors.toSet());
	}
	
	public void addPerfil(Perfil perfil) {
		this.perfis.add(perfil.getCode());
	}
	
	public Cliente() {
		addPerfil(Perfil.CLIENTE);
	}
	
	@PrePersist
	public void hash() {
		this.senha = hashPassword(this.senha);
	}
	
	public String hashPassword(String password) {
		return new BCryptPasswordEncoder().encode(password);
	};
	
	public void setSenha(String senha) {
		this.senha = hashPassword(senha);
	}
	
}
