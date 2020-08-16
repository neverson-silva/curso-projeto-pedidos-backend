package com.neversonsilva.cursomc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoDto {
    
    private Integer numeroDeParcelas;

    private String type;

}