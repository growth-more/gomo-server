package com.gomo.app.core.member.application.port.in;

import java.util.UUID;

import com.gomo.app.core.member.domain.exception.MemberAuthenticationFailedException;

public interface MemberLoginProcessor {

	/**
	 * Login a member using their email and password.
	 *
	 * @param email The member's email address.
	 * @param password The member's plain-text password for verification.
	 * @return The id (UUID) of the login member.
	 * @throws MemberAuthenticationFailedException if the credentials do not match.
	 */
	UUID login(String email, String password);
}
