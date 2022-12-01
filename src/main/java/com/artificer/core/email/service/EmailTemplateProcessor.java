package com.artificer.core.email.service;

import com.artificer.services.EnvioEmailService.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Component
public class EmailTemplateProcessor {

	@Autowired
	private Configuration config;

	protected String processarTemplate(Message mesage) {

		try {
			Template template = config.getTemplate(mesage.getBody());

			return FreeMarkerTemplateUtils.processTemplateIntoString(template, mesage.getModels());
		} catch (Exception e) {
			throw new EmailException("Não foi possível montar o template do e-mail!", e);
		}
	}

}
