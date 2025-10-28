package com.gomo.app.core.interest.application.port.in;

import java.util.UUID;

import com.gomo.app.core.interest.domain.exception.InterestAccessDeniedException;
import com.gomo.app.core.interest.domain.exception.InterestNotFoundException;

public interface DeleteInterestUseCase {

	/**
	 * Deletes a specific interest and all its associated data.
	 * This includes the interest's logo, its designation as a major interest, and any relationships it is part of.
	 *
	 * @param registrantId The id of the registrant requesting the deletion, used to verify authority.
	 * @param interestId   The id of the interest to be deleted.
	 * @throws InterestNotFoundException if no interest exists with the provided ID.
	 * @throws InterestAccessDeniedException if the registrant does not have the authority to delete the specified interest.
	 */
	void delete(UUID registrantId, UUID interestId);
}
