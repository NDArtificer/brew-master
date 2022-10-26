package com.artificer.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.artificer.exceptions.EmailJaCadastradoException;
import com.artificer.exceptions.SenhaNaoInformadaException;
import com.artificer.model.Usuario;
import com.artificer.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {

	@Autowired
	private UsuarioRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Transactional
	public Usuario save(Usuario usuario) {
		Optional<Usuario> usuarioExistente = repository.findByEmail(usuario.getEmail());
		if (usuarioExistente.isPresent()) {
			throw new EmailJaCadastradoException("E-mail informado já está em uso!");
		}
		if (usuario.isNovo() && !StringUtils.hasText(usuario.getSenha())) {
			throw new SenhaNaoInformadaException("O campo senha é obrigtório ao cadastrar novo usuário!");
		}
		if (usuario.isNovo()) {
			usuario.setSenha(this.passwordEncoder.encode(usuario.getSenha()));
			usuario.setConfirmacaoSenha(usuario.getSenha());
		}

		return repository.save(usuario);
	}

}
