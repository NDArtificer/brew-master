package com.artificer.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.artificer.domain.model.Pedido;
import com.artificer.infrastructure.repository.helper.pedido.PedidoQueries;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>, PedidoQueries {

}
