package com.gomo.app.quest.presentation.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.gomo.app.quest.domain.model.QuestType;

import lombok.Getter;

@Getter
public class ReadAssignQuestResponse {

	private UUID id;
	private UUID subjectId;
	private QuestType questType;
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
		QuestType questType,
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

	public static ReadAssignQuestResponse of(
		UUID id,
		UUID subjectId,
		QuestType questType,
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
		return new ReadAssignQuestResponse(
			id, subjectId, questType, point, score, subjectName, content, isConfirmed, isCompleted, proof, startDateTime, displayOrder
		);
	}
}
