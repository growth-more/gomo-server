package com.gomo.app.core.point.application.port.in;

import java.util.UUID;

import com.gomo.app.core.point.domain.exception.PointWalletNotFoundException;

public interface BalanceReader {

	/**
	 * Retrieves the current balance of a specific point wallet.
	 *
	 * @param transactorId The id of the wallet owner whose balance is to be retrieved.
	 * @return The current point balance as an integer.
	 * @throws PointWalletNotFoundException if no wallet is found for the given transactor ID.
	 */
	int find(UUID transactorId);
}
