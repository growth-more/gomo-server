package com.gomo.app.quest.presentation.request;

import java.util.UUID;

import org.jetbrains.annotations.NotNull;

import com.gomo.app.quest.application.port.command.CreateAssignQuestCommand;

import lombok.Getter;

@Getter
public class CreateAssignQuestRequest {

	private UUID subjectId;
	private String subjectName;
	private String questType;
	private String content;

	private CreateAssignQuestRequest(
		UUID subjectId,
		String subjectName,
		String questType,
		String content
	) {
		this.subjectId = subjectId;
		this.subjectName = subjectName;
		this.questType = questType;
		this.content = content;
	}

	public static CreateAssignQuestRequest of(
		UUID subjectId,
		String subjectName,
		String questType,
		String content
	) {
		return new CreateAssignQuestRequest(subjectId, subjectName, questType, content);
	}

	@NotNull
	public CreateAssignQuestCommand toCommand(UUID participantId) {
		return CreateAssignQuestCommand.of(participantId, subjectId, subjectName, questType, content);
	}
}
