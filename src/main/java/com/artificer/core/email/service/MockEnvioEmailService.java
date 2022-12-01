package com.artificer.core.email.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.artificer.services.EnvioEmailService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MockEnvioEmailService implements EnvioEmailService {
	@Autowired
	private EmailTemplateProcessor templateProcessor;

	@Override
	public void enviar(Message message) {

		String body = templateProcessor.processarTemplate(message);

		log.info("Enviando E-mail para o {}\n{} ", message.getRecipients(), body);

	}
}
