package com.gomo.app.quest.presentation.response;

import java.util.UUID;

import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.model.RepeatQuest;

import lombok.Getter;

@Getter
public class ReadRepeatQuestResponse {

	private UUID id;
	private UUID subjectId;
	private QuestType questType;
	private int point;
	private int score;
	private String subjectName;
	private String content;
	private int displayOrder;

	private ReadRepeatQuestResponse(
		UUID id,
		UUID subjectId,
		QuestType questType,
		int point,
		int score,
		String subjectName,
		String content,
		int displayOrder
	) {
		this.id = id;
		this.subjectId = subjectId;
		this.questType = questType;
		this.point = point;
		this.score = score;
		this.subjectName = subjectName;
		this.content = content;
		this.displayOrder = displayOrder;
	}

	public static ReadRepeatQuestResponse of(RepeatQuest repeatQuest, int point, int score) {
		return new ReadRepeatQuestResponse(
			repeatQuest.getId().getId(),
			repeatQuest.getQuest().getSubjectId().getId(),
			repeatQuest.getQuest().getType(),
			point,
			score,
			repeatQuest.getQuest().getSubjectName().toString(),
			repeatQuest.getQuest().getContent().toString(),
			repeatQuest.getDisplayOrder().getDisplayOrder()
		);
	}
}
