package com.artificer.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Endereco {

	private String logradouro;
	private String numero;
	private String CEP;
	private String complemento;
	private Cidade cidade;

}
