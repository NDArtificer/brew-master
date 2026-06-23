package com.artificer.domain.model.enums;

import lombok.Getter;

@Getter
public enum Sabor {

	ADOCICADA("Adocicada"),
	AMARGA("Amarga"),
	FORTE("Forte"),
	FRUTADA("Frutada"),
	SUAVE("Suave");
	
	
	private String descricao;
	
	Sabor(String descricao){
		this.descricao = descricao;
	}
}
