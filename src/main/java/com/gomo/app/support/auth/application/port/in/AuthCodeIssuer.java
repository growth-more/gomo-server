package com.gomo.app.support.auth.application.port.in;

import com.gomo.app.core.member.domain.exception.EmailDuplicatedException;
import com.gomo.app.core.member.domain.exception.MemberNotFoundException;

public interface AuthCodeIssuer {

	/**
	 * Creates and sends an authentication code to a new user's email for verification during the sign-up process.
	 * This method also checks if the email is already in use as a handle.
	 *
	 * @param email The email address to which the sign-up code will be sent.
	 * @throws EmailDuplicatedException if the provided email is already registered as a handle.
	 */
	void issueForSignUp(String email);

	/**
	 * Creates and sends an authentication code to an existing member's email for the purpose of resetting their password.
	 * This method verifies that a member with the specified email exists before sending the code.
	 *
	 * @param email The email address of the member requesting a password reset.
	 * @throws MemberNotFoundException if no member is found with the specified email address.
	 */
	void issueForPasswordReset(String email);
}
