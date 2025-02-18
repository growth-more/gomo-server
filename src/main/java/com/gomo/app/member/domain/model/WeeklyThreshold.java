package com.gomo.app.member.domain.model;

import com.gomo.app.common.domain.ValueObject;

import com.gomo.app.common.exception.PolicyViolationException;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import static com.gomo.app.common.exception.DomainErrorCode.INVALID_PARAMETER;

@Getter
@Embeddable
@ValueObject
public class WeeklyThreshold {

	private static final int MINIMUM_THRESHOLD = 0;
	private static final int MAXIMUM_THRESHOLD = 15;
	private static final int DEFAULT_THRESHOLD = 3;

	private int threshold;

	protected WeeklyThreshold() {
	}

	private WeeklyThreshold(int threshold) {
		isValidSize(threshold);
		this.threshold = threshold;
	}

	public static WeeklyThreshold createDefault() {
		return new WeeklyThreshold(DEFAULT_THRESHOLD);
	}

	public static WeeklyThreshold of(int threshold) {
		return new WeeklyThreshold(threshold);
	}

	public WeeklyThreshold update(int threshold) {
		return WeeklyThreshold.of(threshold);
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
