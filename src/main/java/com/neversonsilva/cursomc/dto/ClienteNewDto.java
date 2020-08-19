package com.neversonsilva.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.neversonsilva.cursomc.services.validation.ClienteInsert;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@ClienteInsert
public class ClienteNewDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NonNull
	@Getter
    @Setter
    @NotEmpty(message = "Preenchimento obrigatorio")
    @Length(min = 5, max = 120,message = "O tamanho deve ser entre 5 e 120 caracteres.")
    private String nome;

	
	@NonNull
	@Getter
    @Setter
    @Email(message = "Email invalido")
    @NotEmpty(message = "Preenchimento obrigatorio")
	private String email;

	@NonNull
	@Getter
    @Setter
    @NotEmpty(message = "Preenchimento obrigatorio")
	private String cpfOuCnpj;

    @NonNull
    @Getter
	@Setter
    private Integer tipo;
    
    @NonNull
    @Getter
    @Setter
    @NotEmpty(message = "Preenchimento obrigatorio")
	private String logradouro;
	
    @NonNull
    @Getter
    @Setter
    @NotEmpty(message = "Preenchimento obrigatorio")
	private String numero;
	
    @NonNull
    @Getter
	@Setter
	private String complemento;
	
    @NonNull
    @Getter
    @Setter
    @NotEmpty(message = "Preenchimento obrigatorio")
	private String bairro;
	
    @NonNull
    @Getter
    @Setter
    @NotEmpty(message = "Preenchimento obrigatorio")
    private String cep;
    
    @NonNull
    @Getter
    @Setter
    @NotEmpty(message = "Preenchimento obrigatorio")
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
    
    @Getter
    @Setter
    @NotEmpty(message="Campo obrigatório")
    private String senha;
    
}