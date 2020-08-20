package com.neversonsilva.cursomc.repositories;

import com.neversonsilva.cursomc.domains.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

}
