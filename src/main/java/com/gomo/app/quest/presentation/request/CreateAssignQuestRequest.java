package com.gomo.app.quest.presentation.request;

import java.util.UUID;

import com.gomo.app.quest.domain.model.QuestType;

import lombok.Getter;

@Getter
public class CreateAssignQuestRequest {

	private UUID subjectId;
	private QuestType questType;
	private String content;

	private CreateAssignQuestRequest(
		UUID subjectId,
		QuestType questType,
		String content
	) {
		this.subjectId = subjectId;
		this.questType = questType;
		this.content = content;
	}

	public static CreateAssignQuestRequest of(
		UUID subjectId,
		QuestType questType,
		String content
	){
		return new CreateAssignQuestRequest(subjectId, questType, content);
	}
}
