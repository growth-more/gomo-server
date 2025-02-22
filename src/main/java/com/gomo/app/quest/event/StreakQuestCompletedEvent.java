package com.gomo.app.quest.event;

import java.time.LocalDateTime;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gomo.app.common.event.Event;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.model.QuestType;

import lombok.Getter;

@Getter
public class StreakQuestCompletedEvent extends Event {

	private ParticipantId participantId;
	private QuestType questType;
	private LocalDateTime questCompletedDateTime;

	private StreakQuestCompletedEvent() {
		super();
	}

	private StreakQuestCompletedEvent(
		ParticipantId participantId,
		QuestType questType,
		LocalDateTime questCompletedDateTime,
		long timestamp
	) {
		super(timestamp);
		this.participantId = participantId;
		this.questType = questType;
		this.questCompletedDateTime = questCompletedDateTime;
	}

	public static StreakQuestCompletedEvent of(
		ParticipantId participantId,
		QuestType questType,
		LocalDateTime questCompletedDateTime,
		long timestamp
	) {
		return new StreakQuestCompletedEvent(participantId, questType, questCompletedDateTime, timestamp);
	}

	public String toJson() {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.registerModule(new JavaTimeModule());
			return objectMapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	public static StreakQuestCompletedEvent fromJson(String json) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.registerModule(new JavaTimeModule());
			return objectMapper.readValue(json, StreakQuestCompletedEvent.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Failed to parse StreakQuestCompletedEvent JSON: " + json, e);
		}
	}
}
