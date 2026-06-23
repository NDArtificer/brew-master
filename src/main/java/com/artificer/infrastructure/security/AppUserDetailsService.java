package com.artificer.infrastructure.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.artificer.domain.exceptions.UsuarioNaoEncontradoException;
import com.artificer.domain.model.Usuario;
import com.artificer.infrastructure.repository.UsuarioRepository;

@Service
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Usuario> usuarioOptional = usuarioRepository.findByEmailAndAtivo(email);

		Usuario usuario = usuarioOptional
				.orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário e/ou senha incorretos!"));
		return new UsuarioSistema(usuario, getPemissoes(usuario));
	}

	private Collection<? extends GrantedAuthority> getPemissoes(Usuario usuario) {

		Set<SimpleGrantedAuthority> authorities = new HashSet<>();

		List<String> permissoes = usuarioRepository.permissoes(usuario);
		permissoes.forEach(permissao -> authorities.add(new SimpleGrantedAuthority(permissao.toUpperCase())));

		return authorities;
	}

}
