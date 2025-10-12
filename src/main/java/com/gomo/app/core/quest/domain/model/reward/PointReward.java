package com.gomo.app.core.quest.domain.model.reward;

import com.gomo.app.common.arch.ValueObject;

@ValueObject
public record PointReward(int amount) {

	public static PointReward of(int amount) {
		return new PointReward(amount);
	}
}
