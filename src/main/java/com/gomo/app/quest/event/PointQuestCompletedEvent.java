package com.gomo.app.quest.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gomo.app.common.event.Event;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.model.PointReward;

import lombok.Getter;

@Getter
public class PointQuestCompletedEvent extends Event {

	private ParticipantId participantId;
	private PointReward pointReward;

	private PointQuestCompletedEvent() {
		super();
	}

	private PointQuestCompletedEvent(
		ParticipantId participantId,
		PointReward pointReward,
		long timestamp
	) {
		super(timestamp);
		this.participantId = participantId;
		this.pointReward = pointReward;
	}

	public static PointQuestCompletedEvent of (
		ParticipantId participantId,
		PointReward pointReward,
		long timestamp
	) {
		return new PointQuestCompletedEvent(participantId, pointReward, timestamp);
	}

	public String toJson() {
		try {
			return new ObjectMapper().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	public static PointQuestCompletedEvent fromJson(String json) {
		try {
			return new ObjectMapper().readValue(json, PointQuestCompletedEvent.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Failed to parse PointQuestCompletedEvent JSON: " + json, e);
		}
	}
}
