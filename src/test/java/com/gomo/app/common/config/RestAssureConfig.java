package com.gomo.app.common.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;

public class RestAssureConfig {

	public static ObjectMapper initObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper()
			.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
			.registerModule(new JavaTimeModule())
			.setSerializationInclusion(JsonInclude.Include.ALWAYS);

		RestAssured.config = new RestAssuredConfig()
			.objectMapperConfig(new ObjectMapperConfig()
				.jackson2ObjectMapperFactory((clazz, charset) -> objectMapper)
			);

		return objectMapper;
	}
}
