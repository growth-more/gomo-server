package com.gomo.app.core.interest.application.port.in;

import java.util.UUID;

import com.gomo.app.core.interest.domain.exception.InterestAccessDeniedException;
import com.gomo.app.core.interest.domain.exception.InterestNotFoundException;
import com.gomo.app.core.interest.domain.exception.MajorInterestDuplicatedException;

public interface CreateMajorInterestUseCase {

	/**
	 * Designates a specific interest as a major interest for the registrant.
	 *
	 * @param registrantId The id of the registrant who owns the interest.
	 * @param interestId   The id of the interest to be designated as a major interest.
	 * @return The {@link UUID} of the newly created major interest mapping.
	 * @throws InterestNotFoundException if no interest exists with the provided ID.
	 * @throws InterestAccessDeniedException if the registrant does not have the authority to modify the specified interest.
	 * @throws MajorInterestDuplicatedException if the interest is already designated as a major interest.
	 */
	UUID create(UUID registrantId, UUID interestId);
}
