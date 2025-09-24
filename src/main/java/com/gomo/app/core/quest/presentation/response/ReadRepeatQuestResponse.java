package com.gomo.app.core.quest.presentation.response;

import java.util.UUID;

import com.gomo.app.core.quest.application.port.dto.RepeatQuestDto;

import lombok.Getter;

@Getter
public class ReadRepeatQuestResponse {

	private UUID id;
	private UUID subjectId;
	private String questType;
	private int point;
	private int score;
	private String subjectName;
	private String content;
	private int displayOrder;

	private ReadRepeatQuestResponse(
		UUID id,
		UUID subjectId,
		String questType,
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

	public static ReadRepeatQuestResponse from(RepeatQuestDto dto) {
		return new ReadRepeatQuestResponse(
			dto.id(),
			dto.subjectId(),
			dto.questType(),
			dto.point(),
			dto.score(),
			dto.subjectName(),
			dto.content(),
			dto.displayOrder()
		);
	}
}
