package com.artificer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.artificer.model.Usuario;
import com.artificer.repository.helper.usuario.UsuariosQueries;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>, UsuariosQueries {
	Optional<Usuario> findByEmail(String email);

	List<Usuario> findByIdIn(Long[] codigos);
}
