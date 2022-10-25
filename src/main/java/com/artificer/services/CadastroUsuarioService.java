package com.artificer.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artificer.exceptions.EmailJaCadastradoException;
import com.artificer.model.Usuario;
import com.artificer.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {

	@Autowired
	private UsuarioRepository repository;

	@Transactional
	public Usuario save(Usuario usuario) {
		Optional<Usuario> usuarioExistente = repository.findByEmail(usuario.getEmail());
		if (usuarioExistente.isPresent()) {
			throw new EmailJaCadastradoException("E-mail informado já está em uso!");
		}
		return repository.save(usuario);
	}

}
