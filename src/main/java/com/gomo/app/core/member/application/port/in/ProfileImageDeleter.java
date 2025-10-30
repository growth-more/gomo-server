package com.gomo.app.core.member.application.port.in;

import java.util.UUID;

import com.gomo.app.core.member.domain.exception.MemberNotFoundException;

public interface ProfileImageDeleter {

	/**
	 * Deletes the profile image for a specific member, reverting it to the default.
	 *
	 * @param memberId The ID of the member whose profile image is to be deleted.
	 * @throws MemberNotFoundException if no member is found with the provided ID.
	 */
	void delete(UUID memberId);
}
