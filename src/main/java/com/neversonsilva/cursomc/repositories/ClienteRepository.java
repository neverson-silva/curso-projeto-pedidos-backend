package com.neversonsilva.cursomc.repositories;

import com.neversonsilva.cursomc.domains.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    
    @Transactional(readOnly = true)
	Optional<Cliente> findByEmail(String email);

}
