package com.artificer.repository.helper.usuario;

import java.util.Optional;

import com.artificer.model.Usuario;

public interface UsuariosQueries {

	Optional<Usuario> findByEmailAndAtivo(String email);
}
