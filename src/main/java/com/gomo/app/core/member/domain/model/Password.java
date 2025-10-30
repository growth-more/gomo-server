package com.gomo.app.core.member.domain.model;

import java.util.regex.Pattern;

import com.gomo.app.common.arch.ValueObject;
import com.gomo.app.core.member.domain.exception.PasswordConstraintViolationException;
import com.gomo.app.core.member.domain.exception.code.PasswordErrorCode;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class Password {

	private static final int MIN_PASSWORD_LENGTH = 8;
	private static final int MAX_PASSWORD_LENGTH = 64;
	private static final Pattern HAS_LOWERCASE = Pattern.compile(".*[a-z].*");
	private static final Pattern HAS_UPPERCASE = Pattern.compile(".*[A-Z].*");
	private static final Pattern HAS_DIGIT = Pattern.compile(".*\\d.*");
	private static final Pattern HAS_SPECIAL_CHAR = Pattern.compile(".*[@#$%^&+=!].*");
	private static final Pattern ALLOWED_CHARS_ONLY = Pattern.compile("^[A-Za-z\\d@#$%^&+=!]+$");

	private String password;

	protected Password() {
	}

	public Password(String password, boolean isRaw) {
		if (isRaw) {
			ensureNotBlank(password);
			ensureValidLength(password);
			ensureValidPasswordRule(password);
		}
		this.password = password;
	}

	public static Password ofRaw(String rawPassword) {
		return new Password(rawPassword, true);
	}

	public static Password ofEncoded(String encodedPassword) {
		return new Password(encodedPassword, false);
	}

	public static Password forOAuth(String memberId) {
		return new Password("OAuth_" + memberId, false);
	}

	private static void ensureNotBlank(String password) {
		if (password == null || password.isBlank()) {
			throw new PasswordConstraintViolationException(PasswordErrorCode.BLANK);
		}
	}

	private static void ensureValidLength(String password) {
		if (password.length() < MIN_PASSWORD_LENGTH) {
			throw new PasswordConstraintViolationException(PasswordErrorCode.TOO_SHORT);
		}

		if (password.length() > MAX_PASSWORD_LENGTH) {
			throw new PasswordConstraintViolationException(PasswordErrorCode.TOO_LONG);
		}
	}

	private static void ensureValidPasswordRule(String password) {
		if (!HAS_LOWERCASE.matcher(password).matches()) {
			throw new PasswordConstraintViolationException(PasswordErrorCode.NO_LOWERCASE);
		}

		if (!HAS_UPPERCASE.matcher(password).matches()) {
			throw new PasswordConstraintViolationException(PasswordErrorCode.NO_UPPERCASE);
		}

		if (!HAS_DIGIT.matcher(password).matches()) {
			throw new PasswordConstraintViolationException(PasswordErrorCode.NO_DIGIT);
		}

		if (!HAS_SPECIAL_CHAR.matcher(password).matches()) {
			throw new PasswordConstraintViolationException(PasswordErrorCode.NO_SPECIAL_CHAR);
		}

		if (!ALLOWED_CHARS_ONLY.matcher(password).matches()) {
			throw new PasswordConstraintViolationException(PasswordErrorCode.FORBIDDEN);
		}
	}
}
