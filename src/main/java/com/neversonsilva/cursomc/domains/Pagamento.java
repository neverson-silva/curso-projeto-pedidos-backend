package com.neversonsilva.cursomc.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.neversonsilva.cursomc.domains.enums.EstadoPagamento;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@EqualsAndHashCode(of= {"id"})
@Inheritance(strategy=InheritanceType.JOINED)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@type")
public abstract class Pagamento  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Getter
	@Setter
	private Integer id;
	
	@NonNull
	private Integer estado;

	@NonNull
	@OneToOne
	@JoinColumn(name="pedido_id")
	@MapsId
	@Getter
	@Setter
	@JsonIgnore
	private Pedido pedido;

	public Pagamento(Integer id, EstadoPagamento estado, Pedido pedido) {
		this.id = id;
		this.setEstado(estado);
		this.pedido = pedido;
	}
	
	public Pagamento(EstadoPagamento estado, Pedido pedido) {
		this.setEstado(estado);
		this.pedido = pedido;
	}

	/**
	 * Definir o estado do Pagamento
	 * @param EstadoPagamento estado
	 */
	public void setEstado(EstadoPagamento estado) {
		this.estado = estado == null ? null : estado.getCod();
	}
	
	public EstadoPagamento getEstado() {
		return EstadoPagamento.toEnum(estado);
	}
}
