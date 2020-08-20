package com.neversonsilva.cursomc.domains;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.neversonsilva.cursomc.domains.enums.EstadoPagamento;
import lombok.*;

import javax.persistence.Entity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(of= {"id"}, callSuper=true)
@JsonTypeName("pagamentoComCartao")
public class PagamentoComCartao extends Pagamento {
	
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Integer numeroDeParcelas;

	public PagamentoComCartao(@NonNull EstadoPagamento estado, @NonNull Pedido pedido, Integer numeroParcelas) {
		super(estado, pedido);
		this.numeroDeParcelas = numeroParcelas;
	}

	public PagamentoComCartao(Integer id, @NonNull EstadoPagamento estado, @NonNull Pedido pedido, Integer numeroParcelas) {
		super(id, estado, pedido);
		this.numeroDeParcelas = numeroParcelas;
	}
	
	

}
