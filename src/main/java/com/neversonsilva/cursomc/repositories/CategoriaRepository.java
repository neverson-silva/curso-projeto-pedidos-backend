package com.neversonsilva.cursomc.repositories;

import com.neversonsilva.cursomc.domains.Categoria;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
	
	
    @EntityGraph(attributePaths = "produtos")
	Optional<Categoria> findById(Integer id);
	

}
