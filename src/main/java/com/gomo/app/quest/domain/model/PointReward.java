package com.gomo.app.quest.domain.model;

import com.gomo.app.common.domain.ValueObject;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class PointReward {

	private int amount;
	private long traceId;

	protected PointReward() {}

	private PointReward(
		int amount,
		long traceId
	) {
		this.amount = amount;
		this.traceId = traceId;
	}

	public static PointReward of(
		int amount,
		long traceId
	) {
		return new PointReward(amount, traceId);
	}
}
