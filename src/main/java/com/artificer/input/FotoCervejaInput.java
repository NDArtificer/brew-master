package com.artificer.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoCervejaInput {

	private String nome;
	private String contentType;
	private String url;

	public FotoCervejaInput(String nome, String contentType, String url) {
		this.nome = nome;
		this.contentType = contentType;
		this.url = url;
	}

}
