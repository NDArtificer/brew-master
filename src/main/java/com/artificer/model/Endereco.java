package com.artificer.model;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Endereco {

	private String logradouro;
	private String numero;
	private String cep;
	private String complemento;

	@ManyToOne(fetch = FetchType.LAZY)
	private Cidade cidade;
	@Transient
	private Estado estado;

}
