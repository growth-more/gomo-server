package com.gomo.app.core.quest.domain.model.reward;

import com.gomo.app.common.arch.ValueObject;

@ValueObject
public record ScoreReward(int score) {

	public static ScoreReward of(int score) {
		return new ScoreReward(score);
	}
}
