package com.gomo.app.quest.event;

import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gomo.app.common.event.Event;
import com.gomo.app.quest.domain.model.ScoreReward;
import com.gomo.app.quest.domain.model.SubjectId;

import lombok.Getter;

@Getter
public class ScoreQuestCompletedEvent extends Event {

	private UUID memberId;
	private SubjectId subjectId;
	private ScoreReward scoreReward;

	private ScoreQuestCompletedEvent() {
		super();
	}

	private ScoreQuestCompletedEvent(
		UUID memberId,
		SubjectId subjectId,
		ScoreReward scoreReward,
		long timestamp
	) {
		super(timestamp);
		this.memberId = memberId;
		this.subjectId = subjectId;
		this.scoreReward = scoreReward;
	}

	public static ScoreQuestCompletedEvent of(
		UUID memberId,
		SubjectId subjectId,
		ScoreReward scoreReward,
		long timestamp
	) {
		return new ScoreQuestCompletedEvent(memberId, subjectId, scoreReward, timestamp);
	}

	public String toJson() {
		try {
			return new ObjectMapper().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	public static ScoreQuestCompletedEvent fromJson(String json) {
		try {
			return new ObjectMapper().readValue(json, ScoreQuestCompletedEvent.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Failed to parse ScoreQuestCompletedEvent JSON: " + json, e);
		}
	}
}
