package com.gomo.app.core.interest.domain.model;

import java.util.UUID;

import com.gomo.app.common.arch.ValueObject;
import com.gomo.app.core.interest.exception.InterestConstraintViolationException;
import com.gomo.app.core.interest.exception.code.InterestErrorCode;

import lombok.Getter;

@Getter
@ValueObject
public class Registrant {

	private UUID id;
	private InterestQuota interestQuota;

	protected Registrant() {
	}

	private Registrant(UUID id, InterestQuota interestQuota) {
		this.id = id;
		this.interestQuota = interestQuota;
	}

	public static Registrant of(UUID id, String subscriptionPlan) {
		InterestQuota interestQuota;
		switch (subscriptionPlan) {
			case "FREE" -> interestQuota = InterestQuota.FREE;
			case "BASIC" -> interestQuota = InterestQuota.BASIC;
			case "PREMIUM" -> interestQuota = InterestQuota.PREMIUM;
			default -> throw new IllegalArgumentException("Unknown subscription plan: " + subscriptionPlan);
		}
		return new Registrant(id, interestQuota);
	}

	public void validateInterestQuota(long interestCount) {
		if (this.interestQuota.isExceed(interestCount)) {
			throw new InterestConstraintViolationException(InterestErrorCode.EXCEED_QUOTA);
		}
	}
}
