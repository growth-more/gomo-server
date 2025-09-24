package com.gomo.app.core.quest.application.port.command;

import java.util.UUID;

public record CreateRepeatQuestCommand(UUID participantId, UUID subjectId, String subjectName, String questType, String content) {

	public static CreateRepeatQuestCommand of(UUID participantId, UUID subjectId, String subjectName, String questType, String content) {
		return new CreateRepeatQuestCommand(participantId, subjectId, subjectName, questType, content);
	}
}
