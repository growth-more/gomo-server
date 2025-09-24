package com.gomo.app.config;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.gomo.app.support.auth.AuthenticationFilter;
import com.gomo.app.support.logging.LoggingFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class FilterRegistrationConfiguration {

	private final LoggingFilter loggingFilter;
	private final AuthenticationFilter authenticationFilter;

	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilterFilterRegistrationBean() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(
			Arrays.asList("http://localhost:63342", "http://localhost:5173", "https://gomo.nurdykim.me/", "https://gomo.ai.kr", "https://dev.gomo.ai.kr"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);

		CorsFilter corsFilter = new CorsFilter(source);
		FilterRegistrationBean<CorsFilter> corsFilterRegistrationBean = new FilterRegistrationBean<>(corsFilter);
		corsFilterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return corsFilterRegistrationBean;
	}

	@Bean
	public FilterRegistrationBean<LoggingFilter> loggingFilterRegistrationBean() {
		FilterRegistrationBean<LoggingFilter> registrationBean = new FilterRegistrationBean<>(loggingFilter);
		registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean<AuthenticationFilter> authenticationFilterRegistrationBean() {
		FilterRegistrationBean<AuthenticationFilter> authFilterRegistrationBean = new FilterRegistrationBean<>(authenticationFilter);
		authFilterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 2);
		return authFilterRegistrationBean;
	}
}
