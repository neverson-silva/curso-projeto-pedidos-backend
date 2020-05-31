package com.neversonsilva.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neversonsilva.cursomc.domains.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

}
