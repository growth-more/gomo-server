package com.gomo.app.core.streak.application.port.in;

import java.util.UUID;

import com.gomo.app.core.streak.application.port.dto.AchieverDto;
import com.gomo.app.core.streak.domain.exception.AchieverNotFoundException;

public interface AchieverReader {

	/**
	 * Retrieves the details of a specific achiever by their ID.
	 *
	 * @param achieverId The ID of the achiever to retrieve.
	 * @return An {@link AchieverDto} containing the details of the found achiever.
	 * @throws AchieverNotFoundException if no achiever exists with the provided ID.
	 */
	AchieverDto read(UUID achieverId);
}
