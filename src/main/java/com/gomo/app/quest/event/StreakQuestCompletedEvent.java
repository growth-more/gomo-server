package com.gomo.app.quest.event;

import java.time.LocalDateTime;

import com.gomo.app.common.event.Event;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.model.QuestType;

import lombok.Getter;

@Getter
public class StreakQuestCompletedEvent extends Event {

	private ParticipantId participantId;
	private QuestType questType;
	private LocalDateTime questCompletedDateTime;
	private long traceId;

	private StreakQuestCompletedEvent(
		ParticipantId participantId,
		QuestType questType,
		LocalDateTime questCompletedDateTime,
		long traceId
	) {
		this.participantId = participantId;
		this.questType = questType;
		this.questCompletedDateTime = questCompletedDateTime;
		this.traceId = traceId;
	}

	public static StreakQuestCompletedEvent of(
		ParticipantId participantId,
		QuestType questType,
		LocalDateTime questCompletedDateTime,
		long traceId
	) {
		return new StreakQuestCompletedEvent(participantId, questType, questCompletedDateTime, traceId);
	}
}
