package com.artificer.repository.helper.usuario;

import java.util.List;
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

	@Override
	public List<String> permissoes(Usuario usuario) {

		return manager.createQuery(
				"select distinct p.nome from Usuario u inner join u.grupos g inner join g.permissoes p where u = :usuario", String.class)
				.setParameter("usuario", usuario)
				.getResultList();
	}

}
