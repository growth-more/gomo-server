package com.gomo.app.core.interest.application.port.in;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.gomo.app.core.interest.domain.exception.InterestNotFoundException;

public interface LogoUpdater {

	/**
	 * Updates the logo for a specific interest.
	 * If a previous custom logo exists, it will be deleted and replaced by the new one.
	 *
	 * @param interestId  The id of the interest to be updated.
	 * @param updatedLogo The new logo image file.
	 * @throws InterestNotFoundException if no interest exists with the provided ID.
	 */
	void update(UUID interestId, MultipartFile updatedLogo);
}
