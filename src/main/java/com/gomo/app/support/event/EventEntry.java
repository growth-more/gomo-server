package com.gomo.app.support.event;

import com.fasterxml.jackson.databind.JsonNode;
import com.gomo.app.common.event.EventStatus;
import com.gomo.app.common.util.JsonParser;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventEntry {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String eventType;

	@Enumerated(EnumType.STRING)
	private EventStatus eventStatus;
	private String payload;
	private long timestamp;

	public EventEntry(String eventEntry) {
		try {
			JsonNode jsonNode = JsonParser.parseNode(eventEntry);
			this.id = jsonNode.get("id").asLong();
			this.eventType = jsonNode.get("eventType").asText();
			this.eventStatus = EventStatus.valueOf(jsonNode.get("eventStatus").asText());
			this.payload = jsonNode.get("payload").asText();
			this.timestamp = jsonNode.get("timestamp").asLong();
		} catch (Exception e) {
			throw new RuntimeException("Failed to parse EventEntry JSON", e);
		}
	}

	private EventEntry(String eventType, EventStatus eventStatus, String payload, long timestamp) {
		this.eventType = eventType;
		this.eventStatus = eventStatus;
		this.payload = payload;
		this.timestamp = timestamp;
	}

	public static EventEntry of(String eventType, String payload, long timestamp) {
		return new EventEntry(eventType, EventStatus.PENDING, payload, timestamp);
	}

	public void update(EventStatus newStatus) {
		this.eventStatus = newStatus;
	}
}
