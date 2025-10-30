package com.gomo.app.core.member.application.port.out;

import java.util.UUID;

public interface MemberLogoutProcessor {

	/**
	 * Invalidates the authentication session for a specific member.
	 *
	 * @param memberId The ID of the member whose authentication session is to be invalidated.
	 */
	void logout(UUID memberId);
}
