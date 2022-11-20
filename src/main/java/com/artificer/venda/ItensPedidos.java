package com.artificer.venda;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.artificer.model.Cerveja;
import com.artificer.model.ItemPedido;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SessionScope
@Component
public class ItensPedidos {

	private List<ItemPedido> itens = new ArrayList<>();

	public BigDecimal getValorTotalPedido() {
		return itens.stream().map(ItemPedido::getValorTotal).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
	}

	public void adicionarItem(Cerveja cerveja, Integer quantidade) {
		Optional<ItemPedido> itemPedidoOptional = buscarItemPorCerveja(cerveja);

		ItemPedido itemPedido = null;
		if (itemPedidoOptional.isPresent()) {
			itemPedido = itemPedidoOptional.get();
			itemPedido.setQuantidade(itemPedido.getQuantidade() + quantidade);
		} else {

			itemPedido = new ItemPedido();
			itemPedido.setCerveja(cerveja);
			itemPedido.setQuantidade(quantidade);
			itemPedido.setValorUnitario(cerveja.getValor());

			itens.add(0, itemPedido);
		}
	}

	public void alterarQuantidadeItens(Cerveja cerveja, Integer quantidade) {
		ItemPedido itemPedido = buscarItemPorCerveja(cerveja).get();
		itemPedido.setQuantidade(quantidade);
	}

	private Optional<ItemPedido> buscarItemPorCerveja(Cerveja cerveja) {
		return itens.stream().filter(item -> item.getCerveja().equals(cerveja)).findAny();
	}

	public int getTotalItens() {
		return itens.size();
	}
}
