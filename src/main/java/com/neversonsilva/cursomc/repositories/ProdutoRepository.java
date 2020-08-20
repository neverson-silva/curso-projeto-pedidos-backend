package com.neversonsilva.cursomc.repositories;

import com.neversonsilva.cursomc.domains.Categoria;
import com.neversonsilva.cursomc.domains.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly=true)
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

//     @Query(" SELECT DISTINCT obj " + 
//           " FROM Produto obj " + 
//           " INNER JOIN obj.categorias cat " + 
//           " WHERE obj.nome LIKE %:nome% " +
//           "     AND cat in :categorias")
    Page<Produto> findDistinctByNomeContainingAndCategoriasIn(
                        String nome, 
                        List<Categoria> categorias, 
                        Pageable pageRequest);
}
