package com.gomo.app.core.quest.domain.model.reward;

import com.gomo.app.common.arch.ValueObject;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class PointReward {

	private int amount;

	protected PointReward() {
	}

	private PointReward(int amount) {
		this.amount = amount;
	}

	public static PointReward of(int amount) {
		return new PointReward(amount);
	}
}
