package com.artificer.venda;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

import com.artificer.model.Cerveja;
import com.artificer.model.ItemPedido;

public class ItensPedidos {

	private String uuid;
	private List<ItemPedido> itens = new ArrayList<>();

	public ItensPedidos(String uuid) {
		this.uuid = uuid;
	}

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

	public void removerItem(Cerveja cerveja) {
		int index = IntStream.range(0, itens.size()).filter(item -> itens.get(item).getCerveja().equals(cerveja))
				.findAny().getAsInt();
		itens.remove(index);
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

	public List<ItemPedido> getItens() {
		return itens;
	}

	public String getUuid() {
		return uuid;
	}

	@Override
	public int hashCode() {
		return Objects.hash(uuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItensPedidos other = (ItensPedidos) obj;
		return Objects.equals(uuid, other.uuid);
	}

}
