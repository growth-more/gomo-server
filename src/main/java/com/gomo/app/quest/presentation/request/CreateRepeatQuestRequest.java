package com.gomo.app.quest.presentation.request;

import java.util.UUID;

import com.gomo.app.quest.domain.model.QuestType;

import lombok.Getter;

@Getter
public class CreateRepeatQuestRequest {

	private UUID subjectId;
	private String subjectName;
	private QuestType questType;
	private String content;

	private CreateRepeatQuestRequest(
		UUID subjectId,
		String subjectName,
		QuestType questType,
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
		QuestType questType,
		String content
	) {
		return new CreateRepeatQuestRequest(subjectId, subjectName, questType, content);
	}
}
