package com.gomo.app.quest.application.port.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.gomo.app.quest.domain.model.AssignQuest;
import com.gomo.app.quest.domain.model.QuestType;

public record CalendarAssignQuestDto(UUID id, QuestType questType, String subjectName, String content, String proof, boolean isCompleted,
									 LocalDateTime completedDateTime) {

	public static CalendarAssignQuestDto of(AssignQuest assignQuest) {
		return new CalendarAssignQuestDto(
			assignQuest.id(),
			assignQuest.getQuest().getType(),
			assignQuest.getQuest().getSubjectName().toString(),
			assignQuest.getQuest().getContent().toString(),
			assignQuest.getProof().toString(),
			assignQuest.isCompleted(),
			assignQuest.getCompletedDateTime()
		);
	}
}
