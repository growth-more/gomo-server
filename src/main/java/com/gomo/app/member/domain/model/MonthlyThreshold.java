package com.gomo.app.member.domain.model;

import com.gomo.app.common.domain.ValueObject;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class MonthlyThreshold {

	private static final int MINIMUM_THRESHOLD = 0;
	private static final int MAXIMUM_THRESHOLD = 15;
	private static final int DEFAULT_THRESHOLD = 3;

	private int threshold;

	protected MonthlyThreshold() {
	}

	private MonthlyThreshold(int threshold) {
		this.threshold = threshold;
	}

	public static MonthlyThreshold createDefault() {
		return new MonthlyThreshold(DEFAULT_THRESHOLD);
	}

	public static MonthlyThreshold of(int threshold) {
		return new MonthlyThreshold(threshold);
	}

	public MonthlyThreshold update(int threshold) {
		if (isValidSize()) {
			return MonthlyThreshold.of(threshold);
		}

		throw new IllegalArgumentException(
			"Threshold limit exceeded. Maximum threshold is " + MAXIMUM_THRESHOLD + ", but: " + threshold);
	}

	private boolean isValidSize() {
		return threshold >= MINIMUM_THRESHOLD && threshold <= MAXIMUM_THRESHOLD;
	}

	@Override
	public String toString() {
		return String.valueOf(this.threshold);
	}
}
