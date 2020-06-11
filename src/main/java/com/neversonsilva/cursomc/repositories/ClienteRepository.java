package com.neversonsilva.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neversonsilva.cursomc.domains.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	

	

}
