package com.artificer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.artificer.model.Pedido;
import com.artificer.repository.helper.pedido.PedidoQueries;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>, PedidoQueries {

}
