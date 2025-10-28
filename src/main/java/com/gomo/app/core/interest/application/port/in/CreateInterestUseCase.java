package com.gomo.app.core.interest.application.port.in;

import java.util.UUID;

import com.gomo.app.core.interest.application.port.command.CreateInterestCommand;
import com.gomo.app.core.interest.domain.exception.InterestConstraintViolationException;

public interface CreateInterestUseCase {

	/**
	 * Creates a new interest with the provided details.
	 *
	 * @param command A {@link CreateInterestCommand} object containing the necessary information to create a new interest,
	 *                such as registrantId, name, logo file, and color code.
	 * @return The {@link UUID} of the newly created interest.
	 * @throws InterestConstraintViolationException if the registrant has reached the maximum number of interests allowed by their subscription plan.
	 */
	UUID create(CreateInterestCommand command);
}
