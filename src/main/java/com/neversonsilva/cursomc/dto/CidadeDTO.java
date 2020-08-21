package com.neversonsilva.cursomc.dto;

import com.neversonsilva.cursomc.domains.Cidade;
import com.neversonsilva.cursomc.domains.Cliente;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class CidadeDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nome;

    public CidadeDTO(Cidade cidade) {
        setId(cidade.getId());
        setNome(cidade.getNome());
    }


}
