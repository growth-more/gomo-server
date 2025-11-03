package com.gomo.app.core.interest.application.port.in;

import java.util.UUID;

import com.gomo.app.core.interest.domain.exception.MajorInterestAccessDeniedException;
import com.gomo.app.core.interest.domain.exception.MajorInterestNotFoundException;

public interface MajorInterestDeleter {

	/**
	 * Deletes the designation of an interest as a major interest.
	 *
	 * @param registrantId    The id of the registrant requesting the deletion, used for authority validation.
	 * @param majorInterestId The id of the major interest mapping to be deleted.
	 * @throws MajorInterestNotFoundException if no major interest mapping exists with the provided ID.
	 * @throws MajorInterestAccessDeniedException if the registrant does not have the authority to delete the specified major interest.
	 */
	void delete(UUID registrantId, UUID majorInterestId);
}
