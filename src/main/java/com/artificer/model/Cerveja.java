package com.artificer.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import com.artificer.model.enums.Origem;
import com.artificer.model.enums.Sabor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Cerveja {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "SKU é Obrigatório")
	@Column
	private String sku;

	@NotBlank(message = "Nome é Obrigatório")
	@Column
	private String nome;

	@NotBlank(message = "Descricao é Obrigatória")
	@Column
	private String descricao;

	@Column
	private BigDecimal valor;

	@Column
	private BigDecimal teorAlcoolico;

	@Column
	private BigDecimal comissao;

	@Column
	private Integer quantidadeEstoque;

	@Enumerated(EnumType.STRING)
	private Sabor sabor;

	@Enumerated(EnumType.STRING)
	private Origem origem;

	@ManyToOne
	@JoinColumn
	private Estilo estilo;

	public Cerveja() {

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cerveja other = (Cerveja) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
