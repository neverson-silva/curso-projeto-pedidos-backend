package com.neversonsilva.cursomc.domains;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.neversonsilva.cursomc.domains.enums.EstadoPagamento;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@EqualsAndHashCode(of= {"id"})
@Inheritance(strategy=InheritanceType.JOINED)
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

	public void setEstado(EstadoPagamento estado) {
		this.estado = estado.getCod();
	}
	
	public EstadoPagamento getEstado() {
		return EstadoPagamento.toEnum(estado);
	}
}
