package com.gomo.app.core.interest.domain.model;

import java.util.UUID;

import com.gomo.app.common.arch.ValueObject;
import com.gomo.app.core.interest.exception.InterestConstraintViolationException;
import com.gomo.app.core.interest.exception.code.InterestErrorCode;

import lombok.Getter;

@Getter
@ValueObject
public class Registrant {

	private RegistrantId id;
	private InterestQuota interestQuota;

	protected Registrant() {
	}

	private Registrant(
		RegistrantId id,
		InterestQuota interestQuota
	) {
		this.id = id;
		this.interestQuota = interestQuota;
	}

	public static Registrant of(
		RegistrantId id,
		String subscriptionPlan
	) {
		InterestQuota interestQuota = null;
		switch (subscriptionPlan) {
			case "FREE" -> interestQuota = InterestQuota.FREE;
			case "BASIC" -> interestQuota = InterestQuota.BASIC;
			case "PREMIUM" -> interestQuota = InterestQuota.PREMIUM;
			default -> throw new IllegalArgumentException("Unknown subscription plan: " + subscriptionPlan);
		}
		return new Registrant(id, interestQuota);
	}

	public UUID uuid() {
		return this.id.getId();
	}

	public void validateInterestQuota(long interestCount) {
		if (this.interestQuota.isExceed(interestCount)) {
			throw new InterestConstraintViolationException(InterestErrorCode.EXCEED_QUOTA);
		}
	}
}
