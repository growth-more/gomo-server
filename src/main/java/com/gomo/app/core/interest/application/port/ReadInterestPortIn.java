package com.gomo.app.core.interest.application.port;

import java.util.List;
import java.util.UUID;

import com.gomo.app.core.interest.application.port.dto.InterestDto;
import com.gomo.app.core.interest.exception.InterestNotFoundException;

public interface ReadInterestPortIn {

	/**
	 * Retrieves a specific interest in its id.
	 *
	 * @param interestId The id of the interest to retrieve.
	 * @return An {@link InterestDto} containing the details of the found interest.
	 * @throws InterestNotFoundException if no interest exists with the provided ID.
	 */
	InterestDto find(UUID interestId);

	/**
	 * Retrieves a list of all interests associated with a specific registrant.
	 *
	 * @param registrantId The id of the registrant (e.g., a user) whose interests are to be retrieved.
	 * @return A list of {@link InterestDto} objects. The list will be empty if the
	 *         registrant has no associated interests; this method does not return null.
	 */
	List<InterestDto> findAll(UUID registrantId);
}
