package com.neversonsilva.cursomc.repositories;

import com.neversonsilva.cursomc.domains.ItemPedido;
import com.neversonsilva.cursomc.domains.ItemPedidoPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, ItemPedidoPK> {

}
