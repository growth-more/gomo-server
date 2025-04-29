package com.gomo.app.interest.domain.model;

import lombok.Getter;

@Getter
public enum InterestQuota {

	FREE(20), BASIC(100), PREMIUM(999);

	private final int maxCount;

	InterestQuota(int maxCount) {
		this.maxCount = maxCount;
	}

	public boolean isExceed(long currentCount) {
		return currentCount >= this.maxCount;
	}
}
