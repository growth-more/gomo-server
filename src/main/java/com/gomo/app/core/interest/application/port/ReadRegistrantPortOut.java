package com.gomo.app.core.interest.application.port;

import java.util.UUID;

import com.gomo.app.core.interest.application.port.dto.RegistrantDto;

public interface ReadRegistrantPortOut {

	/**
	 * Retrieves the essential details of a single registrant by id.
	 *
	 * @param registrantId The id of the registrant to find.
	 * @return A {@link RegistrantDto} containing the registrant's data.
	 */
	RegistrantDto find(UUID registrantId);
}
