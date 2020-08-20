package com.neversonsilva.cursomc.domains;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

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
