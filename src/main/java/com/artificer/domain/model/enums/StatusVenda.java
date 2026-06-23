package com.artificer.domain.model.enums;

import lombok.Getter;

@Getter
public enum StatusVenda {

	ORCAMENTO("Orçamento"), 
	EMITIDA("Emitida"), 
	CANCELADA("Cancelada");

	private String descricao;

	StatusVenda(String descricao) {
		this.descricao = descricao;
	}
}
