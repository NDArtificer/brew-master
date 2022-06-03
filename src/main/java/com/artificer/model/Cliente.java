package com.artificer.model;

import com.artificer.model.enums.TipoPessoa;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Cliente {

	private String nome;
	private String cpfCnpj;
	private TipoPessoa tipoPessoa;
	private String telefone;
	private String email;
	private Estado estado;
	private Cidade cidade;
	private Endereco endereco;

	public Cliente() {

	}
}
