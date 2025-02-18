package com.gomo.app.member.domain.model;

import com.gomo.app.common.domain.ValueObject;

import com.gomo.app.common.exception.PolicyViolationException;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import static com.gomo.app.common.exception.DomainErrorCode.INVALID_PARAMETER;

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
		isValidSize(threshold);
		this.threshold = threshold;
	}

	public static DailyThreshold createDefault() {
		return new DailyThreshold(DEFAULT_THRESHOLD);
	}

	public static DailyThreshold of(int threshold) {
		return new DailyThreshold(threshold);
	}

	public DailyThreshold update(int threshold) {
		return DailyThreshold.of(threshold);
	}

	private void isValidSize(int threshold) {
		if(threshold < MINIMUM_THRESHOLD || threshold > MAXIMUM_THRESHOLD){
			throw new PolicyViolationException(INVALID_PARAMETER ,"Invalid threshold range");
		}
	}

	@Override
	public String toString() {
		return String.valueOf(this.threshold);
	}
}
