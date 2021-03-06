package com.neversonsilva.cursomc.dto;

import com.neversonsilva.cursomc.domains.Cliente;
import com.neversonsilva.cursomc.services.validation.ClienteUpdate;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ClienteUpdate
public class ClienteDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Integer id;

    @NotEmpty(message = "Preenchimento obrigatorio")
    @Length(min = 5, max = 120,message = "O tamanho deve ser entre 5 e 120 caracteres.")
    private String nome;

    @NotEmpty(message = "Preenchimento obrigatorio")
    @Email(message = "Email invalido")
    private String email;

    public ClienteDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.email = cliente.getEmail();
    }
}
