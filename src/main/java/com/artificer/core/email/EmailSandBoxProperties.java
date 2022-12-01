package com.artificer.core.email;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
@ConfigurationProperties("brewmaster.email.sandbox")
public class EmailSandBoxProperties {

	private String destinatario;

}
