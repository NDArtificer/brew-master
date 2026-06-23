package com.artificer.application.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.artificer.application.email.service.MockEnvioEmailService;
import com.artificer.application.email.service.SandboxEnvioEmailService;
import com.artificer.application.email.service.SmtpEnvioEmailService;
import com.artificer.application.services.EnvioEmailService;

@Configuration
public class EmailConfig {

	@Autowired
	private EmailProperties emailProperties;

	@Bean
	public EnvioEmailService envioEmailService() {
		switch (emailProperties.getImpl()) {
		case MOCK:
			return new MockEnvioEmailService();
		case SMTP:
			return new SmtpEnvioEmailService();
		case SANDBOX:
			return new SandboxEnvioEmailService();
		default:
			return null;
		}
	}

}
