package com.artificer.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoCervejaInput {

	private String nome;
	private String contentType;

	public FotoCervejaInput(String nome, String contentType) {
		this.nome = nome;
		this.contentType = contentType;
	}

}
