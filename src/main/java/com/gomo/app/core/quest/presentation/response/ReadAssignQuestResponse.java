package com.gomo.app.core.quest.presentation.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.gomo.app.core.quest.application.port.dto.AssignQuestDto;

import lombok.Getter;

@Getter
public class ReadAssignQuestResponse {

	private UUID id;
	private UUID subjectId;
	private String questType;
	private int point;
	private int score;
	private String subjectName;
	private String content;
	private boolean isConfirmed;
	private boolean isCompleted;
	private String proof;
	private LocalDateTime startDateTime;
	private int displayOrder;

	private ReadAssignQuestResponse(
		UUID id,
		UUID subjectId,
		String questType,
		int point,
		int score,
		String subjectName,
		String content,
		boolean isConfirmed,
		boolean isCompleted,
		String proof,
		LocalDateTime startDateTime,
		int displayOrder
	) {
		this.id = id;
		this.subjectId = subjectId;
		this.questType = questType;
		this.point = point;
		this.score = score;
		this.subjectName = subjectName;
		this.content = content;
		this.isConfirmed = isConfirmed;
		this.isCompleted = isCompleted;
		this.proof = proof;
		this.startDateTime = startDateTime;
		this.displayOrder = displayOrder;
	}

	public static ReadAssignQuestResponse from(AssignQuestDto dto) {
		return new ReadAssignQuestResponse(
			dto.id(),
			dto.subjectId(),
			dto.questType(),
			dto.point(),
			dto.score(),
			dto.subjectName(),
			dto.content(),
			dto.isConfirmed(),
			dto.isCompleted(),
			dto.proof(),
			dto.startDateTime(),
			dto.displayOrder()
		);
	}
}
