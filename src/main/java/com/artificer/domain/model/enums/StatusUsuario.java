package com.artificer.domain.model.enums;

import com.artificer.infrastructure.repository.UsuarioRepository;

public enum StatusUsuario {

	ATIVAR {

		@Override
		public void executar(Long[] codigos, UsuarioRepository usuario) {
			usuario.findByIdIn(codigos).forEach(u -> u.setAtivo(true));
		}

	},
	DESATIVAR {

		@Override
		public void executar(Long[] codigos, UsuarioRepository usuario) {
			usuario.findByIdIn(codigos).forEach(u -> u.setAtivo(false));
		}

	};

	public abstract void executar(Long[] codigos, UsuarioRepository Usuario);

}
