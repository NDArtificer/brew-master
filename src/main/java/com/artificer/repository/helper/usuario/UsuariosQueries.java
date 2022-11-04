package com.artificer.repository.helper.usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.artificer.model.Usuario;
import com.artificer.repository.filter.UsuarioFilter;

public interface UsuariosQueries {

	Optional<Usuario> findByEmailAndAtivo(String email);

	List<String> permissoes(Usuario usuario);
	
	Page<Usuario> filtrar(UsuarioFilter filter, Pageable pageable);
}
