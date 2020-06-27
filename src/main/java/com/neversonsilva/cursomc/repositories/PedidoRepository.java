package com.neversonsilva.cursomc.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neversonsilva.cursomc.domains.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
	
	
	Optional<Pedido> findById(Integer id);
	

}
