package com.gomo.app.quest.presentation.request;

import java.util.UUID;

import com.gomo.app.quest.domain.model.QuestType;

import lombok.Getter;

@Getter
public class CreateAssignQuestRequest {

	private UUID subjectId;
	private String subjectName;
	private QuestType questType;
	private String content;

	private CreateAssignQuestRequest(
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

	public static CreateAssignQuestRequest of(
		UUID subjectId,
		String subjectName,
		QuestType questType,
		String content
	){
		return new CreateAssignQuestRequest(subjectId, subjectName, questType, content);
	}
}
