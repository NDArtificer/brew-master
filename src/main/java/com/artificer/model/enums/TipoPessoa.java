package com.artificer.model.enums;

import com.artificer.model.validation.CnpjGroup;
import com.artificer.model.validation.CpfGroup;

import lombok.Getter;

@Getter
public enum TipoPessoa {

	FISICA("Física", "CPF", "000.000.000-00", CpfGroup.class),
	JURIDICA("Jurídica", "CNPJ", "00.000.000/0000-00", CnpjGroup.class);

	private String descricao;
	private String documento;
	private String mascara;
	private Class<?> group;

	TipoPessoa(String descricao, String documento, String mascara, Class<?> group) {
		this.descricao = descricao;
		this.documento = documento;
		this.mascara = mascara;
		this.group = group;

	}

}
