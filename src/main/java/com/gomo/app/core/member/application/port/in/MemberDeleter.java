package com.gomo.app.core.member.application.port.in;

import java.util.UUID;

import com.gomo.app.core.member.domain.exception.MemberNotFoundException;

public interface MemberDeleter {

	/**
	 * Deletes a member's account and performs associated cleanup tasks, such as deleting authentication tokens.
	 *
	 * @param memberId The ID of the member to be deleted.
	 * @throws MemberNotFoundException if no member is found with the provided ID.
	 */
	void delete(UUID memberId);
}
