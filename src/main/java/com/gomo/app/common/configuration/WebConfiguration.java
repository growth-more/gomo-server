package com.gomo.app.common.configuration;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.gomo.app.common.converter.OctetStreamReadMsgConverter;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

	private final OctetStreamReadMsgConverter octetStreamReadMsgConverter;

	public WebConfiguration(OctetStreamReadMsgConverter octetStreamReadMsgConverter) {
		this.octetStreamReadMsgConverter = octetStreamReadMsgConverter;
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins("http://localhost:63342")
			.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
			.allowedHeaders("*")
			.allowCredentials(true);
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(octetStreamReadMsgConverter);
	}
}
