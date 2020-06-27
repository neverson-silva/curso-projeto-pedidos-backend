package com.neversonsilva.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neversonsilva.cursomc.domains.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {
		

}
