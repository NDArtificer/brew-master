package com.artificer.repository.filter;

import java.math.BigDecimal;

import com.artificer.model.Estilo;
import com.artificer.model.enums.Origem;
import com.artificer.model.enums.Sabor;

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
	private BigDecimal valorDe;
	private BigDecimal valorAte;

}
