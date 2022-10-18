package com.artificer.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Endereco {

	@Column(name = "endereco_logradouro")
	private String logradouro;
	@Column(name = "endereco_numero")
	private String numero;
	@Column(name = "endereco_cep")
	private String cep;
	@Column(name = "endereco_complemento")
	private String complemento;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "endereco_cidade_id")
	private Cidade cidade;
	@Transient
	private Estado estado;

}
