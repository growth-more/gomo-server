package com.gomo.app.core.interest.application.port.in;

import java.util.UUID;

import com.gomo.app.core.interest.domain.exception.InterestNotFoundException;
import com.gomo.app.core.interest.domain.exception.InterestRelationAccessDeniedException;
import com.gomo.app.core.interest.domain.exception.InterestRelationNotFoundException;

public interface DeleteInterestRelationUseCase {

	/**
	 * Deletes a specific interest relationship.
	 *
	 * @param registrantId       The id of the registrant requesting the deletion. This is used to verify authority.
	 * @param interestRelationId The id of the interest relationship to be deleted.
	 * @throws InterestRelationNotFoundException if no interest relationship exists with the provided ID.
	 * @throws InterestRelationAccessDeniedException if the registrant does not have the authority to delete the specified relationship.
	 * @throws InterestNotFoundException if either the parent or child interest does not exist with the provided IDs.
	 */
	void delete(UUID registrantId, UUID interestRelationId);
}
