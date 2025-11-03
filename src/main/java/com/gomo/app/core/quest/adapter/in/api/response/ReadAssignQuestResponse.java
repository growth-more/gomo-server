package com.gomo.app.core.quest.adapter.in.api.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.gomo.app.core.quest.application.port.dto.AssignQuestDto;
import com.gomo.app.core.quest.domain.model.quest.QuestType;

import lombok.Getter;

@Getter
public class ReadAssignQuestResponse {

	private UUID id;
	private QuestType questType;
	private String subjectName;
	private String content;
	private String proof;
	private boolean isCompleted;
	private LocalDateTime completedDateTime;

	private ReadAssignQuestResponse(
		UUID id,
		QuestType questType,
		String subjectName,
		String content,
		String proof,
		boolean isCompleted,
		LocalDateTime completedDateTime
	) {
		this.id = id;
		this.questType = questType;
		this.subjectName = subjectName;
		this.content = content;
		this.proof = proof;
		this.isCompleted = isCompleted;
		this.completedDateTime = completedDateTime;
	}

	public static ReadAssignQuestResponse from(AssignQuestDto dto) {
		return new ReadAssignQuestResponse(
			dto.id(),
			dto.questType(),
			dto.subjectName(),
			dto.content(),
			dto.proof(),
			dto.isCompleted(),
			dto.completedDateTime()
		);
	}
}
