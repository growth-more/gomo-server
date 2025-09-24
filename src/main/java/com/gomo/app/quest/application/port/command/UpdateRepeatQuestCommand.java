package com.gomo.app.quest.application.port.command;

import java.util.UUID;

public record UpdateRepeatQuestCommand(UUID participantId, UUID repeatQuestId, UUID subjectId, String subjectName, String questType, String content) {

	public static UpdateRepeatQuestCommand of(UUID participantId, UUID repeatQuestId, UUID subjectId, String subjectName, String questType, String content) {
		return new UpdateRepeatQuestCommand(participantId, repeatQuestId, subjectId, subjectName, questType, content);
	}
}
