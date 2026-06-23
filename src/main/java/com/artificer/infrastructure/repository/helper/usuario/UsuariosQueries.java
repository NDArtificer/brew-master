package com.artificer.infrastructure.repository.helper.usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.artificer.domain.model.Usuario;
import com.artificer.infrastructure.repository.filter.UsuarioFilter;

public interface UsuariosQueries {

	Optional<Usuario> findByEmailAndAtivo(String email);

	List<String> permissoes(Usuario usuario);

	Page<Usuario> filtrar(UsuarioFilter filter, Pageable pageable);

	Usuario findUserWithGroups(Long id);
}
