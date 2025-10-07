package com.gomo.app.core.interest.domain.model;

import com.gomo.app.common.arch.ValueObject;

import lombok.Getter;

@Getter
@ValueObject
public class LevelThresholdPolicy {

	private int level;
	private int thresholdScore;

	protected LevelThresholdPolicy() {
	}

	private LevelThresholdPolicy(int level, int thresholdScore) {
		this.level = level;
		this.thresholdScore = thresholdScore;
	}

	public static LevelThresholdPolicy of(int level, int thresholdScore) {
		return new LevelThresholdPolicy(level, thresholdScore);
	}
}
