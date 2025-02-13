package com.gomo.app.common.configuration;

import com.gomo.app.common.authentication.AuthArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.gomo.app.common.converter.OctetStreamReadMsgConverter;

import java.util.List;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final AuthArgumentResolver authArgumentResolver;
	private final OctetStreamReadMsgConverter octetStreamReadMsgConverter;

	public WebConfiguration(AuthArgumentResolver authArgumentResolver, OctetStreamReadMsgConverter octetStreamReadMsgConverter) {
        this.authArgumentResolver = authArgumentResolver;
        this.octetStreamReadMsgConverter = octetStreamReadMsgConverter;
	}

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authArgumentResolver);
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
