package com.neversonsilva.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neversonsilva.cursomc.domain.Categoria;

public interface CategoryRepository extends JpaRepository<Categoria, Integer> {

}
