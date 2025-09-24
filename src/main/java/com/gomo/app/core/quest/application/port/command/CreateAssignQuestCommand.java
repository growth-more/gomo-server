package com.gomo.app.core.quest.application.port.command;

import java.util.UUID;

public record CreateAssignQuestCommand(UUID participantId, UUID subjectId, String subjectName, String questType, String content) {

	public static CreateAssignQuestCommand of(UUID participantId, UUID subjectId, String subjectName, String questType, String content) {
		return new CreateAssignQuestCommand(participantId, subjectId, subjectName, questType, content);
	}
}
