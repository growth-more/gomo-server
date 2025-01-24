package com.gomo.app.quest.presentation.request;

import java.util.UUID;

import com.gomo.app.quest.domain.model.QuestType;

import lombok.Getter;

@Getter
public class UpdateRepeatQuestRequest {

	private UUID subjectId;
	private QuestType questType;
	private String content;

	private UpdateRepeatQuestRequest(
		UUID subjectId,
		QuestType questType,
		String content
	) {
		this.subjectId = subjectId;
		this.questType = questType;
		this.content = content;
	}

	public static UpdateRepeatQuestRequest of(
		UUID subjectId,
		QuestType questType,
		String content
	) {
		return new UpdateRepeatQuestRequest(subjectId, questType, content);
	}
}
