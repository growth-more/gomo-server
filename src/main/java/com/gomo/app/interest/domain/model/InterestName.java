package com.gomo.app.interest.domain.model;

import static com.gomo.app.common.exception.DomainErrorCode.*;

import java.util.regex.Pattern;

import com.gomo.app.common.domain.ValueObject;
import com.gomo.app.common.exception.PolicyViolationException;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class InterestName {

	private static final int MIN_LENGTH = 1;
	private static final int MAX_LENGTH = 25;
	private static final Pattern FORBIDDEN_PATTERN = Pattern.compile("[<>&\"';|\\\\{}\\[\\]()`]|(--|/\\*|\\*/)|[\u0000-\u001F\u007F]");

	private String interestName;

	protected InterestName() {}

	private InterestName(String interestName) {
		ensureNotNull(interestName);
		ensureValidLength(interestName);
		ensureNoForbiddenName(interestName);
		this.interestName = interestName;
	}

	public static InterestName of(String interestName) {
		return new InterestName(interestName);
	}

	public InterestName update(String updatedInterestName) {
		return new InterestName(updatedInterestName);
	}

	private void ensureNotNull(String interestName) {
		if(interestName == null) {
			throw new PolicyViolationException(INVALID_PARAMETER, "Interest name cannot be null");
		}
	}

	private void ensureValidLength(String interestName) {
		int length = interestName.length();
		if(length < MIN_LENGTH) {
			throw new PolicyViolationException(INVALID_PARAMETER, "Interest name is too short");
		}

		if(length > MAX_LENGTH) {
			throw new PolicyViolationException(INVALID_PARAMETER, "Interest name is too long");
		}
	}

	private void ensureNoForbiddenName(String interestName) {
		if(FORBIDDEN_PATTERN.matcher(interestName).find()) {
			throw new PolicyViolationException(INVALID_PARAMETER, "Interest name cannot contain forbidden characters");
		}
	}

	@Override
	public String toString() {
		return this.interestName;
	}
}
