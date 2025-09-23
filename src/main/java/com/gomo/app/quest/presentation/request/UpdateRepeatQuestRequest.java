package com.gomo.app.quest.presentation.request;

import java.util.UUID;

import com.gomo.app.quest.application.port.command.UpdateRepeatQuestCommand;

import lombok.Getter;

@Getter
public class UpdateRepeatQuestRequest {

	private UUID subjectId;
	private String subjectName;
	private String questType;
	private String content;

	private UpdateRepeatQuestRequest(UUID subjectId, String subjectName, String questType, String content) {
		this.subjectId = subjectId;
		this.subjectName = subjectName;
		this.questType = questType;
		this.content = content;
	}

	public static UpdateRepeatQuestRequest of(UUID subjectId, String subjectName, String questType, String content) {
		return new UpdateRepeatQuestRequest(subjectId, subjectName, questType, content);
	}

	public UpdateRepeatQuestCommand toCommand(UUID participantId, UUID repeatQuestId) {
		return UpdateRepeatQuestCommand.of(participantId, repeatQuestId, subjectId, subjectName, questType, content);
	}
}
