package com.gomo.app.core.interest.application.port.in;

import java.util.List;
import java.util.UUID;

import com.gomo.app.core.interest.application.port.dto.MajorInterestDto;

public interface ReadMajorInterestUseCase {

	/**
	 * Retrieves a list of all major interests for a specific registrant, sorted by their display order.
	 *
	 * @param registrantId The id of the registrant whose major interests are to be retrieved.
	 * @return A list of {@link MajorInterestDto} objects, sorted by display order.
	 *         The list will be empty if the registrant has no major interests; this method does not return null.
	 */
	List<MajorInterestDto> findAll(UUID registrantId);
}
