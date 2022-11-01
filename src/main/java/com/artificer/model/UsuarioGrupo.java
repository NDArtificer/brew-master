package com.artificer.model;

import java.util.Objects;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "usuario_grupo")
public class UsuarioGrupo {

	@EmbeddedId
	private UsuarioGrupoId id;

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioGrupo other = (UsuarioGrupo) obj;
		return Objects.equals(id, other.id);
	}

}
