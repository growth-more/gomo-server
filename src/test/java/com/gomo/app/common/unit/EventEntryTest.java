package com.gomo.app.common.unit;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import com.fasterxml.jackson.databind.JsonNode;
import com.gomo.app.common.event.EventStatus;
import com.gomo.app.common.util.JsonParser;
import com.gomo.app.support.event.EventEntry;

@DisplayName("[Domain unit]: 이벤트 엔트리 생성 및 수정 테스트")
class EventEntryTest {

	@DisplayName("json 형식의 문자열로 이벤트 엔트리를 생성한다.")
	@Test
	void create_event_entry() {
		try (MockedStatic<JsonParser> mockedJsonParser = mockStatic(JsonParser.class)) {
			String json = "{ \"id\": 1, \"eventType\": \"TEST_EVENT\", \"eventStatus\": \"PENDING\", \"payload\": \"{}\", \"timestamp\": 123456789 }";
			JsonNode mock = getMockJsonNode();
			mockedJsonParser.when(() -> JsonParser.parseNode(json)).thenReturn(mock);

			EventEntry eventEntry = new EventEntry(json);

			assertThat(eventEntry.getId()).isEqualTo(1L);
			assertThat(eventEntry.getEventType()).isEqualTo("TEST_EVENT");
			assertThat(eventEntry.getEventStatus()).isEqualTo(EventStatus.PENDING);
			assertThat(eventEntry.getPayload()).isEqualTo("{}");
			assertThat(eventEntry.getTimestamp()).isEqualTo(123456789L);
		}
	}

	@DisplayName("예외 발생으로 이벤트 엔트리를 생성하지 못한다.")
	@Test
	void create_event_entry_with_json_parse_exception() {
		try (MockedStatic<JsonParser> mockedJsonParser = mockStatic(JsonParser.class)) {
			String json = "{ \"id\": 1, \"eventType\": \"TEST_EVENT\", \"eventStatus\": \"PENDING\", \"payload\": \"{}\", \"timestamp\": 123456789 }";
			mockedJsonParser.when(() -> JsonParser.parseNode(json)).thenThrow(new RuntimeException("JSON parse exception"));

			assertThatThrownBy(() -> new EventEntry(json))
				.isInstanceOf(RuntimeException.class)
				.hasMessage("Failed to parse EventEntry JSON");
		}
	}

	@DisplayName("팩터리 메서드로 이벤트 엔트리를 생성한다.")
	@Test
	void create_event_entry_with_factory_method() {
		EventEntry eventEntry = EventEntry.of("TEST_EVENT", "{}", 123456789L);

		assertThat(eventEntry.getEventType()).isEqualTo("TEST_EVENT");
		assertThat(eventEntry.getPayload()).isEqualTo("{}");
		assertThat(eventEntry.getTimestamp()).isEqualTo(123456789L);
	}

	@DisplayName("팩터리 메서드로 이벤트 엔트리를 생성하면, 이벤트 상태는 PENDING이다.")
	@Test
	void create_event_entry_with_factory_method_pending() {
		EventEntry eventEntry = EventEntry.of("TEST_EVENT", "{}", 123456789L);

		assertThat(eventEntry.getEventStatus()).isEqualTo(EventStatus.PENDING);
	}

	@DisplayName("이벤트 상태를 변경한다.")
	@Test
	void update_event_status() {
		EventEntry eventEntry = EventEntry.of("TEST_EVENT", "{}", 123456789L);
		eventEntry.update(EventStatus.COMPLETED);

		assertThat(eventEntry.getEventStatus()).isEqualTo(EventStatus.COMPLETED);
	}

	private @NotNull JsonNode getMockJsonNode() {
		JsonNode mock = mock(JsonNode.class);
		JsonNode id = mock(JsonNode.class);
		JsonNode eventType = mock(JsonNode.class);
		JsonNode eventStatus = mock(JsonNode.class);
		JsonNode payload = mock(JsonNode.class);
		JsonNode timestamp = mock(JsonNode.class);

		when(mock.get("id")).thenReturn(id);
		when(id.asLong()).thenReturn(1L);

		when(mock.get("eventType")).thenReturn(eventType);
		when(eventType.asText()).thenReturn("TEST_EVENT");

		when(mock.get("eventStatus")).thenReturn(eventStatus);
		when(eventStatus.asText()).thenReturn("PENDING");

		when(mock.get("payload")).thenReturn(payload);
		when(payload.asText()).thenReturn("{}");

		when(mock.get("timestamp")).thenReturn(timestamp);
		when(timestamp.asLong()).thenReturn(123456789L);

		return mock;
	}
}
