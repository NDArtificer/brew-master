package com.artificer.repository.helper.pedido;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.artificer.model.Pedido;
import com.artificer.repository.filter.PedidoFilter;

public interface PedidoQueries {

	Page<Pedido> filtrar(PedidoFilter filter, Pageable pageable);

	Pedido findWithItens(Long id);

}
