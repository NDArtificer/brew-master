package com.artificer.domain.output;

import java.math.BigDecimal;

import lombok.*;
import org.springframework.format.annotation.NumberFormat;

@Getter
@Setter(AccessLevel.PUBLIC)
@AllArgsConstructor
@NoArgsConstructor
public class ValorItensEstoque {

	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal valor;
	private Long totalItens;

}
