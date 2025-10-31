package com.gomo.app.core.interest.application.port.in;

import com.gomo.app.core.interest.application.port.command.OrderUpdateMajorInterestCommand;

public interface MajorInterestOrderUpdater {

	/**
	 * Updates the display order of major interests for a specific registrant.
	 *
	 * @param command A {@link OrderUpdateMajorInterestCommand} containing the registrant's ID and
	 *                a collection of major interest IDs with their new display orders.
	 */
	void update(OrderUpdateMajorInterestCommand command);
}
