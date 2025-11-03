package com.gomo.app.core.interest.application.port.out;

import java.util.UUID;

import com.gomo.app.core.interest.application.port.dto.RegistrantDto;

public interface RegistrantReader {

	/**
	 * Retrieves the essential details of a single registrant by id.
	 *
	 * @param registrantId The id of the registrant to find.
	 * @return A {@link RegistrantDto} containing the registrant's data.
	 */
	RegistrantDto find(UUID registrantId);
}
