package com.artificer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.artificer.model.Cerveja;
import com.artificer.venda.ItensPedidos;

public class ItensPedidosTest {

	private ItensPedidos itensPedidos = new ItensPedidos();

	@Test
	public void calcularValorTotalPedidoSemItens() {
		assertEquals(BigDecimal.ZERO, this.itensPedidos.getValorTotalPedido());

	}

	@Test
	public void calcularValorComUmItem() {
		Cerveja cerveja = new Cerveja();

		BigDecimal valor = new BigDecimal("8.99");
		cerveja.setValor(valor);

		itensPedidos.adicionarItem(cerveja, 1);
		assertEquals(valor, this.itensPedidos.getValorTotalPedido());
	}

	@Test
	public void calcularValorComVariosItens() {
		Cerveja cerveja1 = new Cerveja();

		BigDecimal valor1 = new BigDecimal("8.99");
		cerveja1.setId(1L);
		cerveja1.setValor(valor1);

		Integer quantidade1 = 1;
		itensPedidos.adicionarItem(cerveja1, quantidade1);

		Cerveja cerveja2 = new Cerveja();

		BigDecimal valor2 = new BigDecimal("8.50");
		cerveja1.setId(2L);
		cerveja2.setValor(valor2);
		Integer quantidade2 = 2;

		itensPedidos.adicionarItem(cerveja2, quantidade2);

		BigDecimal valorTotal = valor1.multiply(new BigDecimal(quantidade1))
				.add(valor2.multiply(new BigDecimal(quantidade2)));
		assertEquals(valorTotal, this.itensPedidos.getValorTotalPedido());
	}

	@Test
	public void materOTamanhoDaListaParaMesmasCervejas() {
		Cerveja cerveja1 = new Cerveja();

		BigDecimal valor1 = new BigDecimal("8.99");
		cerveja1.setId(1L);
		cerveja1.setValor(valor1);
		Integer quantidade1 = 1;
		itensPedidos.adicionarItem(cerveja1, quantidade1);
		itensPedidos.adicionarItem(cerveja1, quantidade1);

		assertEquals(1, this.itensPedidos.getTotalItens());
	}

}
