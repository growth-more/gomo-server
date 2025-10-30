package com.gomo.app.core.member.application.port.in;

import com.gomo.app.core.member.domain.exception.HandleDuplicatedException;

public interface HandleValidator {

	/**
	 * Validates for the duplication of a member handle to ensure its uniqueness.
	 *
	 * @param handle The handle to be checked for duplication.
	 * @throws HandleDuplicatedException if the provided handle is already in use by another member.
	 */
	void validateDuplicated(String handle);
}
