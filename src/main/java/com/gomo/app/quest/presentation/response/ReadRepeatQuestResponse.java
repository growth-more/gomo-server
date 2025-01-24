package com.gomo.app.quest.presentation.response;

import java.util.UUID;

import com.gomo.app.quest.domain.model.QuestType;

import lombok.Getter;

@Getter
public class ReadRepeatQuestResponse {

	private UUID id;
	private QuestType questType;
	private String subjectName;
	private String content;
	private int displayOrder;

	private ReadRepeatQuestResponse(
		UUID id,
		QuestType questType,
		String subjectName,
		String content,
		int displayOrder
	) {
		this.id = id;
		this.questType = questType;
		this.subjectName = subjectName;
		this.content = content;
		this.displayOrder = displayOrder;
	}

	public static ReadRepeatQuestResponse of(
		UUID id,
		QuestType questType,
		String subjectName,
		String content,
		int displayOrder
	) {
		return new ReadRepeatQuestResponse(id, questType, subjectName, content, displayOrder);
	}
}
