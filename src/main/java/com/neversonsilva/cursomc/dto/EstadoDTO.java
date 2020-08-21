package com.neversonsilva.cursomc.dto;

import com.neversonsilva.cursomc.domains.Estado;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class EstadoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nome;

    public EstadoDTO(Estado estado) {
        setId(estado.getId());
        setNome(estado.getNome());
    }
}
