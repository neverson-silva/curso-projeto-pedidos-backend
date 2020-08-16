package com.neversonsilva.cursomc.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {
    
    private  ItemIdPedido cliente;

    private ItemIdPedido enderecoEntrega;

    private PagamentoDto pagamento;

    private List<ItemPedidoDto> itens;

}