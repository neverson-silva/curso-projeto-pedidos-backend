package com.neversonsilva.cursomc.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemPedidoDto {
    
    private Integer quantidade;

    private ItemIdPedido produto;
}