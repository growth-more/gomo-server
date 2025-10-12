package com.gomo.app.core.interest.domain.model;

import com.gomo.app.common.arch.ValueObject;

@ValueObject
public record LevelThresholdPolicy(int level, int thresholdScore) {

	public static LevelThresholdPolicy of(int level, int thresholdScore) {
		return new LevelThresholdPolicy(level, thresholdScore);
	}
}
