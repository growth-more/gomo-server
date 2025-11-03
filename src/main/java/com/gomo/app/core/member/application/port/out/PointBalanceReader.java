package com.gomo.app.core.member.application.port.out;

import java.util.UUID;

public interface PointBalanceReader {

	/**
	 * Retrieves the current point balance for a specific member.
	 *
	 * @param memberId The ID of the member whose point balance is to be retrieved.
	 * @return The current point balance as an integer.
	 */
	int read(UUID memberId);
}
