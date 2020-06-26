package com.neversonsilva.cursomc.domains;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@EqualsAndHashCode(of= {"id"})
public class Endereco implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private Integer id;
	
	@NonNull
	private String logradouro;
	
	@NonNull
	private String numero;
	
	@NonNull
	private String complemento;
	
	@NonNull
	private String bairro;
	
	@NonNull
	private String cep;
	
	@NonNull
	@ManyToOne()
	@JoinColumn(name="cliente_id")
	@JsonBackReference
	private Cliente cliente;
	
	@NonNull
	@ManyToOne()
	@JoinColumn(name="cidade_id")
	private Cidade cidade;
}
