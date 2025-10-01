package com.gomo.app.core.point.application.port;

import java.util.UUID;

public interface CreatePointWalletPortIn {

	/**
	 * @return Created point wallet id
	 */
	UUID create(UUID transactorId);
}
