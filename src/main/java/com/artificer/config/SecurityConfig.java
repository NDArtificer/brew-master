package com.artificer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/layout/**", "/images/**", "/styles/**", "/javascripts/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.
		authorizeRequests()
			.antMatchers("/estilos","/cervejas").hasAuthority("PESQUISAR_ESTILO" )
			.antMatchers("/estilos/**").hasAuthority("CADASTRAR_ESTILO")
			.antMatchers("/cidades/**").hasAuthority("CADASTRAR_CIDADE")
			.antMatchers("/usuarios/**").hasAuthority("CADASTRAR_USUARIO")
			.antMatchers("/status/**").hasAuthority("CADASTRAR_USUARIO")
			.antMatchers("/clientes/**").hasAuthority("CADASTRAR_CLIENTE")
			.antMatchers("/cervejas/**", "/estilos/**", "/pedidos/**").hasAuthority("CADASTRAR_CERVEJA")
			.antMatchers("/fotos/**").hasAuthority("ACESSAR_FOTOS")
		.anyRequest()
			.authenticated()
		.and()
			.formLogin()
				.loginPage("/login")
				.permitAll()
			.and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.and()
			.sessionManagement()
			.maximumSessions(1)
			.expiredUrl("/login")
		.and()
			.invalidSessionUrl("/login")
		.and();
	}

}
