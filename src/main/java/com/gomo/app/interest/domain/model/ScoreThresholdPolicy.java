package com.gomo.app.interest.domain.model;

import com.gomo.app.common.domain.ValueObject;

import lombok.Getter;

@Getter
@ValueObject
public class ScoreThresholdPolicy {

	private int level;
	private int threshold;

	protected ScoreThresholdPolicy() {}

	private ScoreThresholdPolicy(int level, int threshold) {
		this.level = level;
		this.threshold = threshold;
	}

	public static ScoreThresholdPolicy of(int level, int threshold) {
		return new ScoreThresholdPolicy(level, threshold);
	}
}
