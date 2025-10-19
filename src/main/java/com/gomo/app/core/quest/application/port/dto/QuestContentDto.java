package com.gomo.app.core.quest.application.port.dto;

import java.util.UUID;

import com.gomo.app.core.quest.domain.model.quest.QuestType;

/**
 * @param questType The quest type (e.g., "DAILY", "WEEKLY"); refer {@link QuestType}.
 */
public record QuestContentDto(UUID participantId, UUID subjectId, String subjectName, String questType, String questContent) {

	public static QuestContentDto of(UUID participantId, UUID subjectId, String subjectName, String questType, String questContent) {
		return new QuestContentDto(participantId, subjectId, subjectName, questType, questContent);
	}
}
