package com.gomo.app.core.member.application.port.in;

import com.gomo.app.core.member.domain.exception.EmailConstraintViolationException;

public interface EmailChecker {

	/**
	 * Checks if an email address is already registered by a member.
	 * This method also implicitly validates the format of the email string.
	 *
	 * @param email The email address to check.
	 * @return {@code true} if a member with the specified email already exists, {@code false} otherwise.
	 * @throws EmailConstraintViolationException if the provided email string does not follow a valid email format.
	 */
	boolean exists(String email);
}
