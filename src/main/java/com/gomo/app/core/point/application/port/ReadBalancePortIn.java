package com.gomo.app.core.point.application.port;

import java.util.UUID;

public interface ReadBalancePortIn {

	/**
	 * Retrieves the balance of a point wallet.
	 *
	 * @param transactorId the ID of the wallet owner
	 * @return the current balance
	 */
	int find(UUID transactorId);
}
