package com.neversonsilva.cursomc.repositories;

import com.neversonsilva.cursomc.domains.Cliente;
import com.neversonsilva.cursomc.domains.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
	
	
	Optional<Pedido> findById(Integer id);

	@Transactional(readOnly = true)
	Page<Pedido> findByCliente(Cliente cliente, Pageable page);
	

}
