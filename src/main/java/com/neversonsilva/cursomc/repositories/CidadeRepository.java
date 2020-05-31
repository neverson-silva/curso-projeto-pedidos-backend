package com.neversonsilva.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neversonsilva.cursomc.domains.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

}
