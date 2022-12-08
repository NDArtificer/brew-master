package com.artificer.service.event.cerveja;

import org.springframework.util.StringUtils;

import com.artificer.model.Cerveja;

import lombok.Getter;

@Getter
public class CervejaSalvaEvent {

	private Cerveja cerveja;

	public CervejaSalvaEvent(Cerveja cerveja) {
		this.cerveja = cerveja;
	}

	public boolean temFoto() {
		return StringUtils.hasText(cerveja.getFoto());
	}

	public boolean isNovaFoto() {
		return cerveja.isNovaFoto();
	}

	public boolean fotoAlterada() {
		return StringUtils.hasText(cerveja.getFotoAtual()) && !cerveja.getFotoAtual().equals(cerveja.getFoto());
	}
}
