package com.gomo.app.quest.application.port.dto;

import java.util.UUID;

/**
 * @param questType
 * DAILY, WEEKLY, MONTHLY
 */
public record QuestContentDto(UUID participantId, UUID subjectId, String subjectName, String questType, String questContent) {

	public static QuestContentDto of(UUID participantId, UUID subjectId, String subjectName, String questType, String questContent) {
		return new QuestContentDto(participantId, subjectId, subjectName, questType, questContent);
	}
}
