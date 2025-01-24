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

	private StreakQuestCompletedEvent(
		ParticipantId participantId,
		QuestType questType,
		LocalDateTime questCompletedDateTime
	) {
		this.participantId = participantId;
		this.questType = questType;
		this.questCompletedDateTime = questCompletedDateTime;
	}

	public static StreakQuestCompletedEvent create(
		ParticipantId participantId,
		QuestType questType,
		LocalDateTime questCompletedDateTime
	) {
		return new StreakQuestCompletedEvent(participantId, questType, questCompletedDateTime);
	}
}
