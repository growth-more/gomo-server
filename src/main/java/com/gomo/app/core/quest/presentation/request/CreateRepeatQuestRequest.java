package com.gomo.app.core.quest.presentation.request;

import java.util.UUID;

import org.jetbrains.annotations.NotNull;

import com.gomo.app.core.quest.application.port.command.CreateRepeatQuestCommand;

import lombok.Getter;

@Getter
public class CreateRepeatQuestRequest {

	private UUID subjectId;
	private String subjectName;
	private String questType;
	private String content;

	private CreateRepeatQuestRequest(
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

	public static CreateRepeatQuestRequest of(
		UUID subjectId,
		String subjectName,
		String questType,
		String content
	) {
		return new CreateRepeatQuestRequest(subjectId, subjectName, questType, content);
	}

	@NotNull
	public CreateRepeatQuestCommand toCommand(UUID participantId) {
		return CreateRepeatQuestCommand.of(participantId, subjectId, subjectName, questType, content);
	}
}
