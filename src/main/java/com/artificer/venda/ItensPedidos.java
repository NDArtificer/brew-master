package com.artificer.venda;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
		ItemPedido itemPedido = new ItemPedido();
		itemPedido.setCerveja(cerveja);
		itemPedido.setQuantidade(quantidade);
		itemPedido.setValorUnitario(cerveja.getValor());

		itens.add(itemPedido);

	}
}
