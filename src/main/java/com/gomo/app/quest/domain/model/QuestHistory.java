package com.gomo.app.quest.domain.model;

import java.time.LocalDateTime;

import com.gomo.app.common.domain.ValueObject;

import lombok.Getter;

@Getter
@ValueObject
public class QuestHistory {

	private Quest quest;
	private LocalDateTime startedDateTime;
	private LocalDateTime completedDateTime;

	protected QuestHistory() {}

	private QuestHistory(
		Quest quest,
		LocalDateTime startedDateTime,
		LocalDateTime completedDateTime
	) {
		this.quest = quest;
		this.startedDateTime = startedDateTime;
		this.completedDateTime = completedDateTime;
	}

	public static QuestHistory of(
		Quest quest,
		LocalDateTime startedDateTime,
		LocalDateTime completedDateTime
	) {
		return new QuestHistory(quest, startedDateTime, completedDateTime);
	}
}
