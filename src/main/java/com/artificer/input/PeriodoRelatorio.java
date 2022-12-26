package com.artificer.input;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PeriodoRelatorio {

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataInicio;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataFim;
}
