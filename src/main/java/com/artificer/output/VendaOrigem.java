package com.artificer.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VendaOrigem {

	private String mes;
	private Integer totalNacional;
	private Integer totalInternacional;
}
