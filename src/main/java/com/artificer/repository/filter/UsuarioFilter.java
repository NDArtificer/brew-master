package com.artificer.repository.filter;

import com.artificer.model.Grupo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioFilter {

	private String nome;
	private String email;
	private Grupo grupos;

}
