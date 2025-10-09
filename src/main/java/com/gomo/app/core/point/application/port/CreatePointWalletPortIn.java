package com.gomo.app.core.point.application.port;

import java.util.UUID;

public interface CreatePointWalletPortIn {

	/**
	 * Creates a new point wallet for the given transactor.
	 *
	 * @param transactorId The id of the entity (e.g., member ID) for whom the wallet is being created.
	 * @return The id (UUID) of the newly created point wallet.
	 * @throws PointWalletAlreadyExistsException if a wallet for the given transactor ID already exists.
	 */
	UUID create(UUID transactorId);
}
