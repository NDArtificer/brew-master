package com.artificer.repository.filter;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import com.artificer.model.enums.StatusVenda;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoFilter {

	private Long id;
	private StatusVenda status;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate desde;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate ate;

	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal valorMinimo;

	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal valorMaximo;

	private String nomeCliente;
	private String cpfCnpjCliente;

}
