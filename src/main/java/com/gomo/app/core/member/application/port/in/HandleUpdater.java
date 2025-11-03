package com.gomo.app.core.member.application.port.in;

import java.util.UUID;

import com.gomo.app.core.member.domain.exception.HandleDuplicatedException;
import com.gomo.app.core.member.domain.exception.MemberNotFoundException;

public interface HandleUpdater {

	/**
	 * Updates the handle for a specific member.
	 * It ensures the new handle is not already in use before applying the change.
	 *
	 * @param memberId The ID of the member whose handle is to be updated.
	 * @param handle   The new handle to be set.
	 * @throws MemberNotFoundException if no member is found with the provided ID.
	 * @throws HandleDuplicatedException if the new handle is already in use by another member.
	 */
	void update(UUID memberId, String handle);
}
