package com.gomo.app.core.member.application.port;

import java.util.UUID;

import com.gomo.app.core.member.exception.MemberAuthenticationFailedException;

public interface LoginMemberPortIn {

	/**
	 * Authenticates a member using their email and password.
	 *
	 * @param email The member's email address.
	 * @param password The member's plain-text password for verification.
	 * @return The id (UUID) of the successfully authenticated member.
	 * @throws MemberAuthenticationFailedException if the credentials do not match.
	 */
	UUID authenticate(String email, String password);
}
