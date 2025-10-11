package com.gomo.app.core.quest.application.port.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.gomo.app.core.quest.domain.model.assign.AssignQuest;

public record AssignQuestDto(UUID id, UUID subjectId, String questType, int point, int score, String subjectName, String content, boolean isConfirmed,
							 boolean isCompleted, String proof, LocalDateTime startDateTime, int displayOrder) {

	public static AssignQuestDto from(AssignQuest assignQuest, int point, int score) {
		return new AssignQuestDto(
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
			assignQuest.displayOrder()
		);
	}
}
