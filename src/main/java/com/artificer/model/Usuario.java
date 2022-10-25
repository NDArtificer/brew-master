package com.artificer.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.artificer.model.validation.AttributeConfirmation;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AttributeConfirmation(attribute = "senha", attributeConfirm = "confirmacaoSenha", message = "As senhas informadas não conferem!")
public class Usuario {

	@javax.persistence.Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	@Column
	@NotBlank(message = "Nome é obrigatorio!")
	private String nome;
	@Column

	@NotBlank(message = "Email é obrigatorio!")
	@Email(message = "Email é obrigatorio!")
	private String email;

	private String senha;

	@Transient
	private String confirmacaoSenha;

	private Boolean ativo;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(columnDefinition = "datetime")
	@NotNull(message = "Data de Nascimento é obrigatorio!")
	private LocalDate dataNascimento;

	// @NotNull(message = "Informe pelo menos um grupo!")
	@ManyToMany
	@JoinTable(name = "usuario_grupo", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "grupo_id"))
	private Set<Grupo> grupos;

	@Override
	public int hashCode() {
		return Objects.hash(Id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(Id, other.Id);
	}

}
