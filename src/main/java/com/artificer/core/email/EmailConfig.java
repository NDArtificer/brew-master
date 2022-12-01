package com.artificer.core.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.artificer.core.email.service.MockEnvioEmailService;
import com.artificer.core.email.service.SandboxEnvioEmailService;
import com.artificer.core.email.service.SmtpEnvioEmailService;
import com.artificer.services.EnvioEmailService;

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
