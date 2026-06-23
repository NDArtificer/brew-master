package com.artificer.infrastructure.repository.filter;

import java.math.BigDecimal;

import org.springframework.format.annotation.NumberFormat;

import com.artificer.domain.model.Estilo;
import com.artificer.domain.model.enums.Origem;
import com.artificer.domain.model.enums.Sabor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CervejaFilter {

	private String sku;
	private String nome;
	private Estilo estilo;
	private Sabor sabor;
	private Origem origem;

	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal valorDe;

	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal valorAte;

}
