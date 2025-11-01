package com.gomo.app.core.point.application.port.in;

import java.util.UUID;

import com.gomo.app.common.web.PageRequest;
import com.gomo.app.core.point.application.port.dto.ListPointDto;

public interface PointReader {

	/**
	 * Retrieves a paginated list of point transaction histories for a specific transactor.
	 *
	 * @param transactorId The ID of the transactor (e.g., a member) whose point history is to be retrieved.
	 * @param pageRequest  A {@link PageRequest} object containing cursor-based pagination details.
	 * @return A {@link ListPointDto} containing the list of point transactions for the requested page.
	 *         The list will be empty if there are no transactions; this method does not return null.
	 */
	ListPointDto readAll(UUID transactorId, PageRequest pageRequest);
}
