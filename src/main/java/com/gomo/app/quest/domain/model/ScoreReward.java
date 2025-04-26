package com.gomo.app.quest.domain.model;

import com.gomo.app.common.ValueObject;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class ScoreReward {

	private int score;

	protected ScoreReward() {}

	private ScoreReward(int score) {
		this.score = score;
	}

	public static ScoreReward of(int score) {
		return new ScoreReward(score);
	}
}
