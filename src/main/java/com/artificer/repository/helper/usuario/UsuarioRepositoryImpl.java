package com.artificer.repository.helper.usuario;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import com.artificer.model.Usuario;

public class UsuarioRepositoryImpl implements UsuariosQueries {

	@Autowired
	private EntityManager manager;

	@Override
	public Optional<Usuario> findByEmailAndAtivo(String email) {
		return manager.createQuery("from Usuario where lower(email) = lower(:email) and ativo = true", Usuario.class)
				.setParameter("email", email).getResultList().stream().findFirst();
	}

}
