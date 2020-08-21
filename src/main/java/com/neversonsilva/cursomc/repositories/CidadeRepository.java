package com.neversonsilva.cursomc.repositories;

import com.neversonsilva.cursomc.domains.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

    @Query("select c " +
            "from Cidade c " +
            "where c.estado.id = :estadoId " +
            "order by c.nome ")
    @Transactional(readOnly = true)
    List<Cidade>findCidades(@Param("estadoId") Integer estadoId);
}
