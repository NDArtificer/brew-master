package com.artificer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // substitui EnableGlobalMethodSecurity
public class SecurityConfig {

	private final UserDetailsService userDetailsService;

	public SecurityConfig(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		var daoAuthenticationProvider = new DaoAuthenticationProvider(userDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests(authz -> authz
						.requestMatchers("/layout/**", "/images/**", "/styles/**", "/javascripts/**").permitAll()
						.requestMatchers("/estilos","/cervejas").hasAuthority("PESQUISAR_ESTILO")
						.requestMatchers("/estilos/**").hasAuthority("CADASTRAR_ESTILO")
						.requestMatchers("/cidades/**").hasAuthority("CADASTRAR_CIDADE")
						.requestMatchers("/usuarios/**").hasAuthority("CADASTRAR_USUARIO")
						.requestMatchers("/status/**").hasAuthority("CADASTRAR_USUARIO")
						.requestMatchers("/clientes/**").hasAuthority("CADASTRAR_CLIENTE")
						.requestMatchers("/cervejas/**", "/estilos/**", "/pedidos/**", "/").hasAuthority("CADASTRAR_CERVEJA")
						.requestMatchers("/fotos/**").hasAuthority("ACESSAR_FOTOS")
						.anyRequest().authenticated()
				)
				.formLogin(form -> form
						.loginPage("/login")
						.permitAll()
				)
				.logout(logout -> logout.logoutUrl("/logout").permitAll()
				)
				.sessionManagement(session -> session
						.maximumSessions(1)
						.expiredUrl("/login")
				)
				.sessionManagement(session -> session
						.invalidSessionUrl("/login")
				);

		return http.build();
	}
}
