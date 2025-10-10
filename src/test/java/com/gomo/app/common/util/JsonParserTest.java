package com.gomo.app.common.util;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.Getter;

@DisplayName("[Common unit]: json 변환 테스트")
public class JsonParserTest {

	@DisplayName("객체를 json으로 변환한다.")
	@Test
	void parse_to_json() {
		JsonStub stub = new JsonStub(1, "stub");
		String json = JsonParser.toJson(stub);

		assertThat(json).isEqualTo("{\"id\":1,\"name\":\"stub\"}");
	}

	@DisplayName("파싱 오류로 인해 객체를 json으로 변환하지 못한다.")
	@Test
	void cannot_parse_to_json() {
		InvalidStub invalidStub = new InvalidStub(1, "stub");

		assertThatThrownBy(() -> JsonParser.toJson(invalidStub))
			.isInstanceOf(RuntimeException.class)
			.hasMessageContaining("Failed to convert InvalidStub to JSON");
	}

	@DisplayName("json을 객체로 변환한다.")
	@Test
	void parse_to_object() {
		String json = "{\"id\":1,\"name\":\"stub\"}";
		JsonStub stub = JsonParser.fromJson(json, JsonStub.class);

		assertThat(stub.getId()).isEqualTo(1);
		assertThat(stub.getName()).isEqualTo("stub");
	}

	@DisplayName("파싱 오류로 인해 json을 객체로 변환하지 못한다.")
	@Test
	void cannot_parse_to_object() {
		String invalidJson = "{\"id\":1,\"name\":stub\"}";

		assertThatThrownBy(() -> JsonParser.fromJson(invalidJson, JsonStub.class))
			.isInstanceOf(RuntimeException.class)
			.hasMessageContaining("Failed to parse JSON into JsonStub");
	}

	@DisplayName("json을 json tree로 변환한다.")
	@Test
	void parse_to_json_tree() {
		String json = "{\"id\":1,\"name\":\"stub\"}";
		JsonNode jsonNode = JsonParser.parseNode(json);

		assertThat(jsonNode.get("id").asInt()).isEqualTo(1);
		assertThat(jsonNode.get("name").asText()).isEqualTo("stub");
	}

	@DisplayName("파싱 오류로 인해 json을 json tree로 변환하지 못한다.")
	@Test
	void cannot_parse_to_json_tree() {
		String invalidJson = "{\"id\":1,\"name\":stub\"}";

		assertThatThrownBy(() -> JsonParser.parseNode(invalidJson))
			.isInstanceOf(RuntimeException.class)
			.hasMessageContaining("Failed to parse JSON into JsonNode");
	}

	@Getter
	static class JsonStub {
		int id;
		String name;

		private JsonStub() {
		}

		public JsonStub(int id, String name) {
			this.id = id;
			this.name = name;
		}
	}

	static class InvalidStub {
		int id;
		String name;

		public InvalidStub(int id, String name) {
			this.id = id;
			this.name = name;
		}
	}
}
