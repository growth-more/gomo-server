package com.gomo.app.core.quest.application.port.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.gomo.app.core.quest.domain.model.assign.AssignQuest;

public record AssignQuestDetailDto(UUID id, UUID subjectId, String questType, int point, int score, String subjectName, String content, boolean isConfirmed,
								   boolean isCompleted, String proof, LocalDateTime startDateTime, LocalDateTime completedDateTime, int displayOrder) {
	public static AssignQuestDetailDto from(AssignQuest assignQuest, int point, int score) {
		return new AssignQuestDetailDto(
			assignQuest.getId(),
			assignQuest.subjectId(),
			assignQuest.questType().name(),
			point,
			score,
			assignQuest.subjectName(),
			assignQuest.content(),
			assignQuest.isConfirmed(),
			assignQuest.isCompleted(),
			assignQuest.getProof().getUrl(),
			assignQuest.getStartDateTime(),
			assignQuest.getCompletedDateTime(),
			assignQuest.displayOrder()
		);
	}
}
