package com.gomo.app.core.quest.domain.model.reward;

import com.gomo.app.common.arch.ValueObject;

@ValueObject
public record QuestReward(ScoreReward scoreReward, PointReward pointReward) {

	public static QuestReward of(ScoreReward scoreReward, PointReward pointReward) {
		return new QuestReward(scoreReward, pointReward);
	}

	public int scoreValue() {
		return this.scoreReward.score();
	}

	public int pointValue() {
		return this.pointReward.amount();
	}
}
