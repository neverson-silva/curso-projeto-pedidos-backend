package com.neversonsilva.cursomc.domains;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(of= {"id"})
@ToString
public class Pedido  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private Integer id;
	
	@NonNull
	@JsonFormat(pattern="dd/MM/yyyy HH:mm")
	private Date instante;
	
	@OneToOne(cascade=CascadeType.ALL, mappedBy="pedido")
	private Pagamento pagamento;

	@NonNull
	@ManyToOne
	@JoinColumn(name="cliente_id")
	private Cliente cliente;

	@NonNull
	@ManyToOne
	@JoinColumn(name="endereco_entrega_id")
	private Endereco enderecoEntrega;
	
	@OneToMany(mappedBy="id.pedido")
	@JsonIgnore
	private Set<ItemPedido> itens = new HashSet<ItemPedido>();

	public Pedido(Integer id, Date instante, Cliente cliente, Endereco enderecoEntrega) {
		super();
		this.id = id;
		this.instante = instante;
		this.cliente = cliente;
		this.enderecoEntrega = enderecoEntrega;
	}
	
	public double getValorTotal()
	{

		return itens.stream()
					.mapToDouble(item -> item.getSubtotal() )
					.reduce(0.0, ( subtotal, valorAtual) -> subtotal + valorAtual);

	}

	
}
