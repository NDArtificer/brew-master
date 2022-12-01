package com.artificer.services;

import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.Singular;

public interface EnvioEmailService {

	void enviar(Message message);

	@Getter
	@Setter
	@Builder
	class Message {

		@Singular
		private Set<String> recipients;

		@NonNull
		private String subject;

		@NonNull
		private String body;

		@Singular
		private Map<String, Object> models;
	}

}
