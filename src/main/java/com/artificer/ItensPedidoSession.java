package com.artificer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.artificer.model.Cerveja;
import com.artificer.model.ItemPedido;
import com.artificer.venda.ItensPedidos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SessionScope
@Component
public class ItensPedidoSession {

	private Set<ItensPedidos> itensPedidos = new HashSet<>();

	public void adicionarItem(String uuid, Cerveja cerveja, int quantidade) {
		ItensPedidos itens = buscarItensPedidosPorUuid(uuid);

		itens.adicionarItem(cerveja, quantidade);
		itensPedidos.add(itens);

	}

	private ItensPedidos buscarItensPedidosPorUuid(String uuid) {
		ItensPedidos itens = itensPedidos.stream().filter(item -> item.getUuid().equals(uuid)).findAny()
				.orElse(new ItensPedidos(uuid));
		return itens;
	}

	public void alterarQuantidadeItens(String uuid, Cerveja cerveja, Integer quantidade) {
		ItensPedidos itens = buscarItensPedidosPorUuid(uuid);
		itens.alterarQuantidadeItens(cerveja, quantidade);

	}

	public void removerItem(String uuid, Cerveja cerveja) {
		ItensPedidos itens = buscarItensPedidosPorUuid(uuid);
		itens.removerItem(cerveja);
	}

	public List<ItemPedido> getItens(String uuid) {
		// TODO Auto-generated method stub
		return buscarItensPedidosPorUuid(uuid).getItens();
	}

	public Object getValorTotal(String uuid) {
		ItensPedidos itens = buscarItensPedidosPorUuid(uuid);
		return itens.getValorTotalPedido();
	}

}
