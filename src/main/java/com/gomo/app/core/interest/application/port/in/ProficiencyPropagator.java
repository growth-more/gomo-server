package com.gomo.app.core.interest.application.port.in;

import java.util.UUID;

public interface ProficiencyPropagator {

	/**
	 * Propagates changes of the proficiency score of a specific interest in a given amount.
	 *
	 * @param interestId The id of the interest to be adjusted.
	 * @param deltaScore The change in score to be applied.
	 *                   This value can be positive to increase proficiency or negative to decrease it.
	 */
	void propagate(UUID interestId, int deltaScore);
}
