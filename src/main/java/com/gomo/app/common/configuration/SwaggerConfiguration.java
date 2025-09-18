package com.gomo.app.common.configuration;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"dev", "local"})
public class SwaggerConfiguration {

	@Bean
	public GroupedOpenApi diagnosticApi() {
		return GroupedOpenApi.builder()
			.group("Diagnostic")
			.pathsToMatch("/simulators/**")
			.build();
	}

	@Bean
	public GroupedOpenApi businessApi() {
		return GroupedOpenApi.builder()
			.group("Business")
			.pathsToExclude("/simulators/**")
			.build();
	}
}
