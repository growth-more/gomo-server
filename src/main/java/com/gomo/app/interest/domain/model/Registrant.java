package com.gomo.app.interest.domain.model;

import java.util.UUID;

import com.gomo.app.common.ValueObject;
import com.gomo.app.interest.exception.InterestConstraintViolationException;
import com.gomo.app.interest.exception.code.InterestErrorCode;

import lombok.Getter;

@Getter
@ValueObject
public class Registrant {

	private RegistrantId id;
	private InterestQuota interestQuota;

	protected Registrant() {
	}

	public Registrant(
		RegistrantId id,
		InterestQuota interestQuota
	) {
		this.id = id;
		this.interestQuota = interestQuota;
	}

	public UUID uuid() {
		return this.id.getId();
	}

	public static Registrant of(
		RegistrantId id,
		InterestQuota interestQuota
	) {
		return new Registrant(id, interestQuota);
	}

	public void validateInterestQuota(long interestCount) {
		if (this.interestQuota.isExceed(interestCount)) {
			throw new InterestConstraintViolationException(InterestErrorCode.EXCEED_QUOTA);
		}
	}
}
