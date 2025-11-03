package com.gomo.app.core.member.application.port.in;

import java.util.UUID;

import com.gomo.app.core.member.domain.exception.MemberNotFoundException;

public interface WidgetUpdater {

	/**
	 * Updates the widget snapshot for a specific member.
	 * The snapshot typically contains the state or configuration of a UI widget.
	 *
	 * @param memberId The ID of the member whose widget snapshot is to be updated.
	 * @param snapshot The new widget snapshot, likely in a serialized format like JSON.
	 * @throws MemberNotFoundException if no member is found with the provided ID.
	 */
	void update(UUID memberId, String snapshot);
}
