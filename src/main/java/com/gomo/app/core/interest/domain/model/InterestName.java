package com.gomo.app.core.interest.domain.model;

import java.util.regex.Pattern;

import com.gomo.app.common.arch.ValueObject;
import com.gomo.app.core.interest.domain.exception.InterestNameConstraintViolationException;
import com.gomo.app.core.interest.domain.exception.code.InterestNameErrorCode;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class InterestName {

	private static final int MAX_LENGTH = 25;
	private static final Pattern FORBIDDEN_PATTERN = Pattern.compile("[<>&\"';|\\\\{}\\[\\]()`]|(--|/\\*|\\*/)|[\u0000-\u001F\u007F]");

	private String interestName;

	protected InterestName() {
	}

	private InterestName(String interestName) {
		ensureNotBlank(interestName);
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

	private void ensureNotBlank(String interestName) {
		if (interestName == null || interestName.isBlank()) {
			throw new InterestNameConstraintViolationException(InterestNameErrorCode.BLANK);
		}
	}

	private void ensureValidLength(String interestName) {
		if (interestName.length() > MAX_LENGTH) {
			throw new InterestNameConstraintViolationException(InterestNameErrorCode.TOO_LONG);
		}
	}

	private void ensureNoForbiddenName(String interestName) {
		if (FORBIDDEN_PATTERN.matcher(interestName).find()) {
			throw new InterestNameConstraintViolationException(InterestNameErrorCode.FORBIDDEN);
		}
	}

	@Override
	public String toString() {
		return this.interestName;
	}
}
