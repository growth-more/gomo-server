package com.gomo.app.core.interest.application.port.in;

import java.util.UUID;

import com.gomo.app.core.interest.application.port.dto.InterestNetworkDto;

public interface InterestNetworkReader {

	/**
	 * Retrieves the complete network of interests and their relationships for a specific registrant.
	 * The network consists of all interests (nodes) and the relationships between them (edges).
	 *
	 * @param registrantId The id of the registrant whose interest network is to be retrieved.
	 * @return An {@link InterestNetworkDto} containing a list of all interests and their relationships.
	 *         Returns a DTO with empty lists if the registrant has no interests; this method does not return null.
	 */
	InterestNetworkDto read(UUID registrantId);
}
