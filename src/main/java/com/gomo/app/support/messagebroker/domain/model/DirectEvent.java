package com.gomo.app.support.messagebroker.domain.model;

import java.util.UUID;

import com.fasterxml.jackson.databind.JsonNode;
import com.gomo.app.common.util.JsonParser;

import lombok.Getter;

@Getter
public class DirectEvent {

	private UUID id;
	private String eventName;
	private String payload;

	public DirectEvent(String eventEntry) {
		try {
			JsonNode jsonNode = JsonParser.parseNode(eventEntry);
			this.id = UUID.fromString(jsonNode.get("id").asText());
			this.eventName = jsonNode.get("eventName").asText();
			this.payload = jsonNode.get("payload").asText();
		} catch (Exception e) {
			throw new RuntimeException("Failed to parse EventEntry JSON", e);
		}
	}

	private DirectEvent(UUID id, String eventName, String payload) {
		this.id = id;
		this.eventName = eventName;
		this.payload = payload;
	}

	public static DirectEvent of(UUID id, String eventName, String payload) {
		return new DirectEvent(id, eventName, payload);
	}
}
