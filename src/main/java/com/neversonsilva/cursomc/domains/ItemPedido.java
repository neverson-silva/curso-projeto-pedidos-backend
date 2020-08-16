package com.neversonsilva.cursomc.domains;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of= {"id"})
public class ItemPedido implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	@JsonIgnore
	private ItemPedidoPK id;// = new ItemPedidoPK();

	private Double desconto;

	private Integer quantidade;

	private Double preco;

	public ItemPedido(Pedido pedido, Produto produto, Double desconto, Integer quantidade, Double preco) {
		super();
		this.id = new ItemPedidoPK(pedido, produto);
		this.desconto = desconto;
		this.quantidade = quantidade;
		this.preco = preco;
	}

	public ItemPedido(Pedido pedido, Produto produto) {
		super();
		this.id = new ItemPedidoPK(pedido, produto);
	}
	
	@JsonIgnore
	public Pedido getPedido() {
		return this.id.getPedido();
	}
	
	public Produto getProduto() {
		return id.getProduto();
	}

	public void setPedido(Pedido pedido) {
		id.setPedido(pedido);
	}

	public void setProduto(Produto produto) {
		id.setProduto(produto);
	}

	public double getSubtotal()
	{
		return (preco - desconto) * quantidade;
	}

}
