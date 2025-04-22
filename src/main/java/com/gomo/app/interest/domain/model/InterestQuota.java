package com.gomo.app.interest.domain.model;

import com.gomo.app.interest.exception.InterestErrorCode;
import com.gomo.app.interest.exception.InterestQuotaExceededException;

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
			throw new InterestQuotaExceededException(InterestErrorCode.INTEREST_QUOTA_EXCEEDED);
		}
	}
}
