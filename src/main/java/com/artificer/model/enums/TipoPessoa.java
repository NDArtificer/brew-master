package com.artificer.model.enums;

import com.artificer.model.validation.CnpjGroup;
import com.artificer.model.validation.CpfGroup;

import lombok.Getter;

@Getter
public enum TipoPessoa {

	FISICA("Física", "CPF", "000.000.000-00", CpfGroup.class) {
		@Override
		public String formatar(String cpfCnpj) {

			return cpfCnpj.replaceAll("(\\d{3})(\\d{3})(\\d{3})", "$1.$2.$3-");
		}
	},
	JURIDICA("Jurídica", "CNPJ", "00.000.000/0000-00", CnpjGroup.class) {
		@Override
		public String formatar(String cpfCnpj) {
			// TODO Auto-generated method stub
			return cpfCnpj.replaceAll("(\\d{2}(\\d{3})(\\d{3})(\\d{4})", "$1.$2.$3/$4-");
		}
	};

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

	public abstract String formatar(String cpfCnpj);

	public static String removerFormatacao(String cpfCnpj) {
		return cpfCnpj.replaceAll("[^0-9]", "");
	}

}
