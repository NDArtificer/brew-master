package com.artificer.security;

import java.util.HashSet;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.artificer.exceptions.UsuarioNaoEncontradoException;
import com.artificer.model.Usuario;
import com.artificer.repository.UsuarioRepository;

@Service
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Usuario> usuarioOptional = usuarioRepository.findByEmailAndAtivo(email);

		Usuario usuario = usuarioOptional
				.orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário e/ou senha incorretos!"));
		return new User(usuario.getEmail(), usuario.getSenha(), new HashSet<>());
	}

}
