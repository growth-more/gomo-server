package com.gomo.app.quest.presentation.request;

import java.util.UUID;

import com.gomo.app.quest.domain.model.QuestType;

import lombok.Getter;

@Getter
public class UpdateAssignQuestRequest {

	private UUID subjectId;
	private QuestType questType;
	private String content;

	private UpdateAssignQuestRequest(
		UUID subjectId,
		QuestType questType,
		String content
	) {
		this.subjectId = subjectId;
		this.questType = questType;
		this.content = content;
	}

	public static UpdateAssignQuestRequest of(
		UUID subjectId,
		QuestType questType,
		String content
	) {
		return new UpdateAssignQuestRequest(subjectId, questType, content);
	}
}
