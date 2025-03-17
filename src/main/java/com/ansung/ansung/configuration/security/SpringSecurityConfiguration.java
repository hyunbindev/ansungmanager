package com.ansung.ansung.configuration.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.ansung.ansung.filter.JwtTokenFilter;

@Configuration
public class SpringSecurityConfiguration {
	private final HandlerExceptionResolver resolver;
	
	public SpringSecurityConfiguration(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
		this.resolver=resolver;
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http.authorizeHttpRequests(this::setAuthorizeRequests)
			.csrf(AbstractHttpConfigurer::disable)
			.headers((headers)->headers.frameOptions((HeadersConfigurer.FrameOptionsConfig::disable)))
			.sessionManagement((session)->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.addFilterBefore(new JwtTokenFilter(resolver), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	private void setAuthorizeRequests(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry) {
		registry.requestMatchers("/api/auth")
		.permitAll()
		.anyRequest()
		.authenticated();
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
