package com.gomo.app.quest.application.port.command;

import java.util.UUID;

public record UpdateAssignQuestCommand(UUID participantId, UUID assignQuestId, UUID subjectId, String subjectName, String questType, String content) {

	public static UpdateAssignQuestCommand of(UUID participantId, UUID assignQuestId, UUID subjectId, String subjectName, String questType, String content) {
		return new UpdateAssignQuestCommand(participantId, assignQuestId, subjectId, subjectName, questType, content);
	}
}
