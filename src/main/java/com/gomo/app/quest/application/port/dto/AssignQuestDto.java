package com.gomo.app.quest.application.port.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.gomo.app.quest.domain.model.AssignQuest;

public record AssignQuestDto(UUID id, UUID subjectId, String questType, int point, int score, String subjectName, String content, boolean isConfirmed,
							 boolean isCompleted, String proof, LocalDateTime startDateTime, int displayOrder) {

	public static AssignQuestDto from(AssignQuest assignQuest, int point, int score) {
		return new AssignQuestDto(
			assignQuest.id(),
			assignQuest.getQuest().getSubjectId().getId(),
			assignQuest.getQuest().getType().name(),
			point,
			score,
			assignQuest.getQuest().getSubjectName().toString(),
			assignQuest.getQuest().getContent().getQuestContent(),
			assignQuest.isConfirmed(),
			assignQuest.isCompleted(),
			assignQuest.getProof().getUrl(),
			assignQuest.getStartDateTime(),
			assignQuest.getDisplayOrder().getDisplayOrder()
		);
	}
}
