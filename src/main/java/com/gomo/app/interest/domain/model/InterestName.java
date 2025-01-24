package com.gomo.app.interest.domain.model;

import com.gomo.app.common.domain.ValueObject;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class InterestName {

	private String interestName;

	protected InterestName() {}

	private InterestName(String interestName) {
		this.interestName = interestName;
	}

	public static InterestName of(String interestName) {
		return new InterestName(interestName);
	}

	private boolean isValidLength(int min, int max) {
		return false;
	}

	private boolean doesContainProhibitCharacters() {
		return false;
	}
}
