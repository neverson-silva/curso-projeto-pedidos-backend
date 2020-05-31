package com.neversonsilva.cursomc.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.neversonsilva.cursomc.domains.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
	
	
    @EntityGraph(attributePaths = "produtos")
	Optional<Categoria> findById(Integer id);
	

}
