package com.gomo.app.core.member.application.port.in;

import com.gomo.app.core.member.domain.exception.MemberNotFoundException;

public interface PasswordResetter {

	/**
	 * Resets the password for a member.
	 *
	 * @param email          The email address of the member for whom the password reset is being performed.
	 * @param newPassword    The new raw password to be set.
	 * @throws MemberNotFoundException if no member is found with the specified email address.
	 */
	void reset(String email, String newPassword);
}
