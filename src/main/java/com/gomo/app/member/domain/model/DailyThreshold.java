package com.gomo.app.member.domain.model;

import com.gomo.app.common.domain.ValueObject;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class DailyThreshold {

	private static final int MINIMUM_THRESHOLD = 0;
	private static final int MAXIMUM_THRESHOLD = 15;
	private static final int DEFAULT_THRESHOLD = 5;

	private int threshold;

	protected DailyThreshold() {
	}

	private DailyThreshold(int threshold) {
		this.threshold = threshold;
	}

	public static DailyThreshold createDefault() {
		return new DailyThreshold(DEFAULT_THRESHOLD);
	}

	public static DailyThreshold of(int threshold) {
		return new DailyThreshold(threshold);
	}

	public DailyThreshold update(int threshold) {
		if (isValidSize()) {
			return DailyThreshold.of(threshold);
		}

		throw new IllegalArgumentException("Threshold limit exceeded. Maximum threshold is " + MAXIMUM_THRESHOLD + ", but: " + threshold);
	}

	private boolean isValidSize() {
		return threshold >= MINIMUM_THRESHOLD && threshold <= MAXIMUM_THRESHOLD;
	}

	@Override
	public String toString() {
		return String.valueOf(this.threshold);
	}
}
