package com.gomo.app.interest.domain.model;

import com.gomo.app.interest.exception.InterestConstraintViolationException;
import com.gomo.app.interest.exception.code.InterestErrorCode;

import lombok.Getter;

@Getter
public enum InterestQuota {

	FREE(20), BASIC(100), PREMIUM(999);

	private final int maxCount;

	InterestQuota(int maxCount) {
		this.maxCount = maxCount;
	}

	public void validateCount(long currentCount) {
		if (currentCount >= maxCount) {
			throw new InterestConstraintViolationException(InterestErrorCode.EXCEED_QUOTA);
		}
	}
}
