package com.neversonsilva.cursomc.domains;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;


@EqualsAndHashCode
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedidoPK implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Getter
	@Setter
	@ManyToOne
	@JoinColumn(name="pedido_id")
	private Pedido pedido;
	
	@Getter
	@Setter
	@ManyToOne
	@JoinColumn(name="produto_id")
	private Produto produto;
	

}
