package com.artificer.infrastructure.repository.filter;

import com.artificer.domain.model.Estado;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeFilter {
	
	private String nome;
	private Estado estado;

}
