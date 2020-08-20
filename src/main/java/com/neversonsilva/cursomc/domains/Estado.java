package com.neversonsilva.cursomc.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of= {"id"})
@Getter
@Setter
@Entity
public class Estado implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private Integer id;
	
	@NonNull
	private String nome;
	
	@OneToMany(mappedBy="estado", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Cidade> cidades = new ArrayList<Cidade>();

	public Estado(Integer id, @NonNull String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}

	
}
