package com.gomo.app.core.interest.application.port.in;

import com.gomo.app.core.interest.application.port.command.UpdateInterestCommand;
import com.gomo.app.core.interest.domain.exception.InterestAccessDeniedException;
import com.gomo.app.core.interest.domain.exception.InterestNotFoundException;

public interface InterestUpdater {

	/**
	 * Updates the properties of an existing interest, such as its name and color code.
	 *
	 * @param command A {@link UpdateInterestCommand} containing the details for the update,
	 *                including the interest ID, the registrant ID for validation, and the new values.
	 * @throws InterestNotFoundException if no interest exists with the ID provided in the command.
	 * @throws InterestAccessDeniedException if the registrant does not have the authority to modify the specified interest.
	 */
	void update(UpdateInterestCommand command);
}
