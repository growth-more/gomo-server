package com.gomo.app.core.member.domain.model;

import java.util.regex.Pattern;

import com.gomo.app.common.arch.ValueObject;
import com.gomo.app.core.member.exception.PasswordConstraintViolationException;
import com.gomo.app.core.member.exception.code.PasswordErrorCode;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class Password {

	private static final int MIN_PASSWORD_LENGTH = 8;
	private static final int MAX_PASSWORD_LENGTH = 64;
	private static final Pattern PASSWORD_RULE_PATTERN = Pattern.compile(
		"^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])[A-Za-z\\d@#$%^&+=!]+$");

	private String password;
	// todo jhl221123: raw 를 상태로 전환해야합니다.

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
		if (!PASSWORD_RULE_PATTERN.matcher(password).matches()) {
			// TODO jhl221123 : 금지 문자, 형식 위반(대/소문자, 특수, 숫자 최소 1개 포함)을 분리해야 합니다.
			throw new PasswordConstraintViolationException(PasswordErrorCode.FORBIDDEN);
		}
	}
}
