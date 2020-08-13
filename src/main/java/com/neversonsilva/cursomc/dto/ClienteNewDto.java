package com.neversonsilva.cursomc.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class ClienteNewDto implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

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
    @Getter
	@Setter
    private Integer tipo;
    
    @NonNull
    @Getter
	@Setter
	private String logradouro;
	
    @NonNull
    @Getter
	@Setter
	private String numero;
	
    @NonNull
    @Getter
	@Setter
	private String complemento;
	
    @NonNull
    @Getter
	@Setter
	private String bairro;
	
    @NonNull
    @Getter
	@Setter
    private String cep;
    
    @NonNull
    @Getter
	@Setter
    private String telefone1;
    
    @Getter
	@Setter
    private String telefone2;

    @Getter
	@Setter
    private String telefone3;

    @Getter
	@Setter
    private Integer cidadeId;
    
}