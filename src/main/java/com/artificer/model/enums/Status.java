package com.artificer.model.enums;

import lombok.Getter;

@Getter
public enum Status {
	ATIVO("Ativo"), 
	INATIVO("Inativo");
	
	
	private String descricao;
	
	Status(String descricao){
		this.descricao = descricao;
	}
}
