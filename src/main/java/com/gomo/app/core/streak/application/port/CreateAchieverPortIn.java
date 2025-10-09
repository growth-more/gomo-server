package com.gomo.app.core.streak.application.port;

import java.util.UUID;

public interface CreateAchieverPortIn {

	/**
	 * Creates a new achiever profile within the streak system.
	 *
	 * @param achieverId The id of the entity (e.g., member ID) to be registered as an achiever.
	 * @return The id (UUID) of the newly created achiever.
	 * @throws AchieverAlreadyExistsException if an achiever for the given ID already exists.
	 */
	UUID create(UUID achieverId);
}
