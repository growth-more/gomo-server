package com.gomo.app.core.quest.presentation.request;

import java.util.UUID;

import com.gomo.app.core.quest.application.port.command.UpdateAssignQuestCommand;

import lombok.Getter;

@Getter
public class UpdateAssignQuestRequest {

	private UUID subjectId;
	private String subjectName;
	private String questType;
	private String content;

	private UpdateAssignQuestRequest(UUID subjectId, String subjectName, String questType, String content) {
		this.subjectId = subjectId;
		this.subjectName = subjectName;
		this.questType = questType;
		this.content = content;
	}

	public static UpdateAssignQuestRequest of(UUID subjectId, String subjectName, String questType, String content) {
		return new UpdateAssignQuestRequest(subjectId, subjectName, questType, content);
	}

	public UpdateAssignQuestCommand toCommand(UUID participantId, UUID assignQuestId) {
		return UpdateAssignQuestCommand.of(participantId, assignQuestId, subjectId, subjectName, questType, content);
	}
}
