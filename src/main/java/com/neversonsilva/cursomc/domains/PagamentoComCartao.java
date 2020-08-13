package com.neversonsilva.cursomc.domains;

import javax.persistence.Entity;

import com.neversonsilva.cursomc.domains.enums.EstadoPagamento;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(of= {"id"}, callSuper=true)
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
