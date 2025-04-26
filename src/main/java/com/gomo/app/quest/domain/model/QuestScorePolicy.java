package com.gomo.app.quest.domain.model;

import com.gomo.app.common.ValueObject;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Getter
@ValueObject
public class QuestScorePolicy {

	@Enumerated(value = EnumType.STRING)
	private QuestType questType;
	private int score;

	protected QuestScorePolicy() {}

	private QuestScorePolicy(
		QuestType questType,
		int score
	) {
		this.questType = questType;
		this.score = score;
	}

	public static QuestScorePolicy of(
		QuestType questType,
		int score
	) {
		return new QuestScorePolicy(questType, score);
	}
}
