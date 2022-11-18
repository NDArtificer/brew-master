package com.artificer.output;

import java.math.BigDecimal;
import java.util.Objects;

import org.springframework.util.StringUtils;

import com.artificer.model.enums.Origem;
import com.google.common.base.Strings;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CervejaSummary {

	private Long id;
	private String sku;
	private String nome;
	private String origem;
	private BigDecimal valor;
	private String foto;

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
		CervejaSummary other = (CervejaSummary) obj;
		return Objects.equals(id, other.id);
	}

	public CervejaSummary(Long id, String sku, String nome, Origem origem, BigDecimal valor, String foto) {
		this.id = id;
		this.sku = sku;
		this.nome = nome;
		this.origem = origem.getDescricao();
		this.valor = valor;
		this.foto = StringUtils.hasText(foto) ? foto : "cerveja-mock.png";
	}

}
