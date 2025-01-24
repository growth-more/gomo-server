package com.gomo.app.quest.presentation.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.gomo.app.quest.domain.model.QuestType;

import lombok.Getter;

@Getter
public class HistoryReadAssignQuestResponse {

	private UUID id;
	private QuestType questType;
	private String subjectName;
	private String content;
	private String proof;
	private boolean isCompleted;
	private LocalDateTime completedDateTime;
	private int weekOfYear;

	private HistoryReadAssignQuestResponse(
		UUID id,
		QuestType questType,
		String subjectName,
		String content,
		String proof,
		boolean isCompleted,
		LocalDateTime completedDateTime,
		int weekOfYear
	) {
		this.id = id;
		this.questType = questType;
		this.subjectName = subjectName;
		this.content = content;
		this.proof = proof;
		this.isCompleted = isCompleted;
		this.completedDateTime = completedDateTime;
		this.weekOfYear = weekOfYear;
	}

	public static HistoryReadAssignQuestResponse of(
		UUID id,
		QuestType questType,
		String subjectName,
		String content,
		String proof,
		boolean isCompleted,
		LocalDateTime completedDateTime,
		int weekOfYear
	) {
		return new HistoryReadAssignQuestResponse(
			id, questType, subjectName, content, proof, isCompleted, completedDateTime, weekOfYear
		);
	}
}
