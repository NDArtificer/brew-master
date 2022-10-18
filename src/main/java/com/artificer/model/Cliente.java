package com.artificer.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.hibernate.validator.group.GroupSequenceProvider;

import com.artificer.model.enums.TipoPessoa;
import com.artificer.model.validation.ClienteGroupSequenceProvider;
import com.artificer.model.validation.CnpjGroup;
import com.artificer.model.validation.CpfGroup;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@GroupSequenceProvider(ClienteGroupSequenceProvider.class)
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String nome;

	@NotBlank
	@CPF(groups = CpfGroup.class)
	@CNPJ(groups = CnpjGroup.class)
	private String cpfCnpj;

	@Column
	@Enumerated(EnumType.STRING)
	private TipoPessoa tipoPessoa;

	@Column
	private String telefone;

	@Column
	@Email
	private String email;

	@Embedded
	private Endereco endereco;

	@PrePersist
	@PreUpdate
	private void removerFormatacaoCpfCnpj() {
		this.cpfCnpj = TipoPessoa.removerFormatacao(this.cpfCnpj);
	}

	public String getCpfCnpjSemFormatacao() {
		return TipoPessoa.removerFormatacao(this.cpfCnpj);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(id, other.id);
	}

}
