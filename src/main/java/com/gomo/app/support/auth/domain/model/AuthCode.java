package com.gomo.app.support.auth.domain.model;

import java.util.Random;

import com.gomo.app.common.arch.ValueObject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@ValueObject
public class AuthCode {

	private static final Random RANDOM = new Random();

	private final String value;

	private AuthCode(String value) {
		if (value == null || !value.matches("\\d{6}")) {
			throw new IllegalArgumentException("Auth code value must be 6 digits");
		}
		this.value = value;
	}

	public static AuthCode generate() {
		String randomCode = String.format("%06d", RANDOM.nextInt(1000000));
		return new AuthCode(randomCode);
	}

	public static AuthCode from(String value) {
		return new AuthCode(value);
	}

	public boolean matches(AuthCode other) {
		return this.value.equals(other.value);
	}
}
