package com.artificer.output;

import java.math.BigDecimal;

import org.springframework.format.annotation.NumberFormat;

public class ValorItensEstoque {

	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal valor;
	private Long totalItens;

	public ValorItensEstoque() {

	}

	public ValorItensEstoque(BigDecimal valor, Long totalItens) {
		this.valor = valor;
		this.totalItens = totalItens;
	}

	public BigDecimal getValor() {
		return valor != null ? valor : BigDecimal.ZERO;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Long getTotalItens() {
		return totalItens != null ? totalItens : 0L;
	}

	public void setTotalItens(Long totalItens) {
		this.totalItens = totalItens;
	}

}
