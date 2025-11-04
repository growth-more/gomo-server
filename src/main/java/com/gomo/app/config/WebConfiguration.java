package com.gomo.app.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.gomo.app.common.session.SessionArgumentResolver;
import com.gomo.app.common.web.OctetStreamReadMsgConverter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfiguration implements WebMvcConfigurer {

	private final SessionArgumentResolver sessionArgumentResolver;
	private final OctetStreamReadMsgConverter octetStreamReadMsgConverter;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(sessionArgumentResolver);
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(octetStreamReadMsgConverter);
	}
}
