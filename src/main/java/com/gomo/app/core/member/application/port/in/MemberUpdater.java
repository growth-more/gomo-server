package com.gomo.app.core.member.application.port.in;

import java.util.UUID;

import com.gomo.app.core.member.domain.exception.MemberNotFoundException;

public interface MemberUpdater {

	/**
	 * Updates the basic information (name and motto) for a specific member.
	 *
	 * @param id    The ID of the member to be updated.
	 * @param name  The new name to be set.
	 * @param motto The new motto to be set.
	 * @throws MemberNotFoundException if no member is found with the provided ID.
	 */
	void update(UUID id, String name, String motto);
}
