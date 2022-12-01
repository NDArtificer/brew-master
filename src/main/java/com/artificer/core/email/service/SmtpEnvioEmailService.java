package com.artificer.core.email.service;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.artificer.core.email.EmailProperties;
import com.artificer.services.EnvioEmailService;

public class SmtpEnvioEmailService implements EnvioEmailService {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private EmailProperties emailProperties;

	@Autowired
	private EmailTemplateProcessor templateProcessor;

	@Override
	public void enviar(Message message) {

		try {
			String body = templateProcessor.processarTemplate(message);
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper mimeHelper = new MimeMessageHelper(mimeMessage, "UTF-8");

			mimeHelper.setFrom(emailProperties.getRemetente());
			mimeHelper.setTo(message.getRecipients().toArray(new String[0]));
			mimeHelper.setSubject(message.getSubject());
			mimeHelper.setText(body, true);

			mailSender.send(mimeMessage);
		} catch (Exception e) {
			throw new EmailException("Não foi possível enviar o e-mail!", e);
		}
	}

}
