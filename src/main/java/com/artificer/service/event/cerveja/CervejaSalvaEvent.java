package com.artificer.service.event.cerveja;

import org.springframework.util.StringUtils;

import com.artificer.model.Cerveja;

import lombok.Getter;

@Getter
public class CervejaSalvaEvent {

	public CervejaSalvaEvent(Cerveja cerveja) {
		this.cerveja = cerveja;
	}

	private Cerveja cerveja;

	public boolean temFoto() {
		return StringUtils.hasText(cerveja.getFoto());
	}
}
