package com.gomo.app.core.member.application.port.in;

import com.gomo.app.core.member.domain.exception.MemberNotFoundException;

public interface PasswordResetter {

	/**
	 * Resets the password for a member after validating a temporary token.
	 *
	 * @param email          The email address of the member for whom the password reset is being performed.
	 * @param newPassword    The new raw password to be set.
	 * @param temporaryToken The verification token required to authorize the password change.
	 * @throws MemberNotFoundException if no member is found with the specified email address.
	 * @throws IllegalArgumentException if the provided temporary token is invalid.
	 */
	void reset(String email, String newPassword, String temporaryToken);
}
