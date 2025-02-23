package com.gomo.app.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonParser {

	private static final ObjectMapper OBJECT_MAPPER = createObjectMapper();

	private JsonParser() {
	}

	private static ObjectMapper createObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		return objectMapper;
	}

	public static String toJson(Object object) {
		try {
			return OBJECT_MAPPER.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Failed to convert " + object.getClass().getSimpleName() + " to JSON", e);
		}
	}

	public static <T> T fromJson(String json, Class<T> clazz) {
		try {
			return OBJECT_MAPPER.readValue(json, clazz);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Failed to parse JSON into " + clazz.getSimpleName() + ": " + json, e);
		}
	}

	public static JsonNode parseNode(String json) {
		try {
			return OBJECT_MAPPER.readTree(json);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Failed to parse JSON into JsonNode: " + json, e);
		}
	}
}
