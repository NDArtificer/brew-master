package com.artificer.model.enums;

import lombok.Getter;

@Getter
public enum Origem {
	NACIONAL("Nacional"),
	INTERNACIONAL("Internacional");

	private String descricao;
	
	Origem(String descricao){
		this.descricao = descricao;
	}
}
