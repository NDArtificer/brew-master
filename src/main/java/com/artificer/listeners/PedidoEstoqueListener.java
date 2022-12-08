package com.artificer.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.artificer.events.PedidoEstoqueEvent;
import com.artificer.model.Cerveja;
import com.artificer.model.Pedido;
import com.artificer.model.enums.StatusVenda;
import com.artificer.repository.CervejasRepository;

@Component
public class PedidoEstoqueListener {

	@Autowired
	private CervejasRepository repository;

	@EventListener
	public void atulizarEstoque(PedidoEstoqueEvent event) {
		Pedido pedido = event.getPedido();
		pedido.getItens().forEach(item -> {
			Integer quantidade;
			Cerveja cerveja = repository.findById(item.getCerveja().getId()).get();
			if(pedido.getStatus().equals(StatusVenda.CANCELADA)) {
				quantidade = cerveja.getQuantidadeEstoque() + item.getQuantidade();
			} else {
				quantidade = (cerveja.getQuantidadeEstoque() - item.getQuantidade());
			}
			cerveja.setQuantidadeEstoque(quantidade);
			repository.save(cerveja);
		});
	}

}
