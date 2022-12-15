package com.artificer.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.util.StringUtils;

import com.artificer.listeners.CervejaEntityListener;
import com.artificer.model.annotations.Sku;
import com.artificer.model.enums.Origem;
import com.artificer.model.enums.Sabor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@EntityListeners(CervejaEntityListener.class)
public class Cerveja extends AbstractAggregateRoot<Cerveja> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Sku
	@NotBlank(message = "SKU é Obrigatório!")
	@Column
	private String sku;

	@NotBlank(message = "Nome é Obrigatório!")
	@Column
	private String nome;

	@NotBlank(message = "Descrição é Obrigatória!")
	@Column
	private String descricao;

	@NumberFormat(pattern = "#,##0.00")
	@NotNull(message = "Valor é obrigatorio!")
	@DecimalMax(value = "9999999.99", message = "Valor da Cerveja deve ser menor que ou igual a R$ 9.999.999,99")
	@Column
	private BigDecimal valor;

	@NumberFormat(pattern = "##0.00")
	@NotNull(message = "Teor Alcoolico é obrigatorio!")
	@DecimalMax(value = "100.0", message = "Teor Alcóolico deve ser menor que ou igual a 100")
	@Column
	private BigDecimal teorAlcoolico;

	@NumberFormat(pattern = "##0.00")
	@NotNull(message = "Comissão é obrigatoria!")
	@DecimalMax(value = "100.0", message = "A comissão deve ser menor que ou igual a 100")
	@Column
	private BigDecimal comissao;

	@NumberFormat(pattern = "#,##0")
	@NotNull(message = "A quantidade de estoque é obrigatoria!")
	@Max(value = 9999, message = "Quantidade de estoque deve ser inferior a 9.999")
	@Column
	private Integer quantidadeEstoque;

	@NotNull(message = "Sabor é obrigatorio!")
	@Enumerated(EnumType.STRING)
	private Sabor sabor;

	@NotNull(message = "Origem é obrigatoria!")
	@Enumerated(EnumType.STRING)
	private Origem origem;

	@NotNull(message = "O Estilo é obrigatorio!")
	@ManyToOne
	@JoinColumn
	private Estilo estilo;

	@Column
	private String foto;

	@Column
	private String contentType;

	@Transient
	private boolean novaFoto;

	@Transient
	private String urlFoto;

	@Transient
	private String urlThumbnailFoto;

	@Transient
	private String fotoAtual;

	public Cerveja() {

	}

	public String getFotoOrMock() {
		return StringUtils.hasText(foto) ? foto : "cerveja-mock.png";
	}

	public boolean isNovaCerveja() {
		return id == null;
	}

	@PrePersist
	@PreUpdate
	private void upperCaseSkuPrePersistOrUpdate() {
		sku = sku.toUpperCase();
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
