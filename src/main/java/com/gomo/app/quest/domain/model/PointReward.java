package com.gomo.app.quest.domain.model;

import com.gomo.app.common.domain.ValueObject;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class PointReward {

	private int points;
	private long traceId;

	protected PointReward() {}

	private PointReward(
		int points,
		long traceId
	) {
		this.points = points;
		this.traceId = traceId;
	}

	public static PointReward of(
		int points,
		long traceId
	) {
		return new PointReward(points, traceId);
	}
}
