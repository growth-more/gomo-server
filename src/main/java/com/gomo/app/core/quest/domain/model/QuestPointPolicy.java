package com.gomo.app.core.quest.domain.model;

import com.gomo.app.common.ValueObject;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Getter
@ValueObject
public class QuestPointPolicy {

	@Enumerated(value = EnumType.STRING)
	private QuestType questType;
	private int points;

	protected QuestPointPolicy() {}

	private QuestPointPolicy(
		QuestType questType,
		int points
	) {
		this.questType = questType;
		this.points = points;
	}

	public static QuestPointPolicy of(
		QuestType questType,
		int points
	) {
		return new QuestPointPolicy(questType, points);
	}
}
