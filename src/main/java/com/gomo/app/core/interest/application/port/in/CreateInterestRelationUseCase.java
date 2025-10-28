package com.gomo.app.core.interest.application.port.in;

import java.util.UUID;

import com.gomo.app.core.interest.domain.exception.InterestNotFoundException;

public interface CreateInterestRelationUseCase {

	/**
	 * Creates a parent-child relationship between two interests for a specific registrant.
	 *
	 * @param registrantId     The id of the registrant who is creating the relationship.
	 * @param parentInterestId The id of the interest that will be the parent.
	 * @param childInterestId  The id of the interest that will be the child.
	 * @return The {@link UUID} of the newly created interest relationship.
	 * @throws InterestNotFoundException if either the parent or child interest does not exist with the provided IDs.
	 */
	UUID create(UUID registrantId, UUID parentInterestId, UUID childInterestId);
}
