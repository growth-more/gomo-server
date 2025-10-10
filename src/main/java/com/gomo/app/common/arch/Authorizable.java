package com.gomo.app.common.arch;

import java.util.UUID;

public interface Authorizable {

	/**
	 * Validates whether the current user has the authority to perform actions on this entity.
	 * If the user does not have the required authority, a runtime exception is thrown.
	 *
	 * @param accessorId the UUID of the entity's owner.
	 */
	void validateAuthority(UUID accessorId);
}
