package com.artificer.infrastructure.repository.filter;

import java.util.List;

import com.artificer.domain.model.Grupo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioFilter {

	private String nome;
	private String email;
	private List<Grupo> grupos;

}
