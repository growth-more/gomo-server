package com.gomo.app.core.member.domain.model;

import java.util.regex.Pattern;

import com.gomo.app.common.arch.ValueObject;
import com.gomo.app.core.member.domain.exception.EmailConstraintViolationException;
import com.gomo.app.core.member.domain.exception.code.EmailErrorCode;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class Email {

	private static final int MAX_EMAIL_LENGTH = 254; // RFC 5322
	private static final int MIN_EMAIL_LENGTH = 10;
	private static final Pattern EMAIL_RULES = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

	private String email;

	protected Email() {
	}

	private Email(String email) {
		ensureNotBlank(email);
		ensureValidEmailLength(email);
		ensureValidEmailRule(email);
		this.email = email;
	}

	public static Email of(String email) {
		return new Email(email);
	}

	private void ensureNotBlank(String email) {
		if (email == null || email.isBlank()) {
			throw new EmailConstraintViolationException(EmailErrorCode.BLANK);
		}
	}

	private void ensureValidEmailLength(String email) {
		if (email.length() < MIN_EMAIL_LENGTH) {
			throw new EmailConstraintViolationException(EmailErrorCode.TOO_SHORT);
		}

		if (email.length() > MAX_EMAIL_LENGTH) {
			throw new EmailConstraintViolationException(EmailErrorCode.TOO_LONG);
		}
	}

	private void ensureValidEmailRule(String email) {
		if (!EMAIL_RULES.matcher(email).matches()) {
			throw new EmailConstraintViolationException(EmailErrorCode.INVALID_FORMAT);
		}
	}

	@Override
	public String toString() {
		return this.email;
	}
}
