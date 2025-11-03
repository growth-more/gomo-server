package com.gomo.app.core.member.application.port.in;

import java.util.UUID;

import com.gomo.app.core.member.domain.exception.MemberAuthenticationFailedException;
import com.gomo.app.core.member.domain.exception.MemberNotFoundException;

public interface PasswordUpdater {

	/**
	 * Updates the password for a specific member after verifying their current password.
	 *
	 * @param memberId       The ID of the member whose password is to be updated.
	 * @param originPassword The member's current (original) password for verification.
	 * @param newPassword    The new password to be set.
	 * @throws MemberNotFoundException if no member is found with the provided ID.
	 * @throws MemberAuthenticationFailedException if the provided original password does not match the member's current password.
	 */
	void update(UUID memberId, String originPassword, String newPassword);
}
