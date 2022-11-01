package com.artificer.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ManyToAny;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class UsuarioGrupoId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;
	@ManyToOne
	@JoinColumn(name = "grupo_id")
	private Grupo grupo;

	@Override
	public int hashCode() {
		return Objects.hash(grupo, usuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioGrupoId other = (UsuarioGrupoId) obj;
		return Objects.equals(grupo, other.grupo) && Objects.equals(usuario, other.usuario);
	}

}
