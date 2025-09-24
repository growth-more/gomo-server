package com.gomo.app.core.quest.application.port.dto;

import java.util.UUID;

import com.gomo.app.core.quest.domain.model.RepeatQuest;

public record RepeatQuestDto(UUID id, UUID subjectId, String questType, int point, int score, String subjectName, String content, int displayOrder
) {

	public static RepeatQuestDto from(RepeatQuest repeatQuest, int point, int score) {
		return new RepeatQuestDto(
			repeatQuest.getId().getId(),
			repeatQuest.getQuest().getSubjectId().getId(),
			repeatQuest.getQuest().getType().name(),
			point,
			score,
			repeatQuest.getQuest().getSubjectName().toString(),
			repeatQuest.getQuest().getContent().toString(),
			repeatQuest.getDisplayOrder().getDisplayOrder()
		);
	}
}
