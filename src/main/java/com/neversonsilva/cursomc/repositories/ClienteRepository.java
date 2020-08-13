package com.neversonsilva.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import com.neversonsilva.cursomc.domains.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    
    @Transactional(readOnly = true)
	Optional<Cliente> findByEmail(String email);

}
