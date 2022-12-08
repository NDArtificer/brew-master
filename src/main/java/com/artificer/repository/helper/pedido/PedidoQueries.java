package com.artificer.repository.helper.pedido;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.artificer.model.Pedido;
import com.artificer.output.VendaMes;
import com.artificer.output.VendaOrigem;
import com.artificer.repository.filter.PedidoFilter;

public interface PedidoQueries {

	Page<Pedido> filtrar(PedidoFilter filter, Pageable pageable);

	Pedido findWithItens(Long id);

	BigDecimal valorTotalVendasAno();

	BigDecimal valorTotalVendasMes();

	BigDecimal valorTicketMedio();

	List<VendaMes> totalVendaMes();

	List<VendaOrigem> totalVendaOrigem();
}
