package com.gomo.app.quest.domain.model;

import com.gomo.app.common.domain.ValueObject;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class ScoreReward {

	private int score;
	private long traceId;

	protected ScoreReward() {}

	private ScoreReward(
		int score,
		long traceId
	) {
		this.score = score;
		this.traceId = traceId;
	}

	static ScoreReward of(
		int score,
		long traceId
	) {
		return new ScoreReward(score, traceId);
	}
}
