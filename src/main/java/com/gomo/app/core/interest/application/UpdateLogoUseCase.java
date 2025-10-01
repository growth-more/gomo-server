package com.gomo.app.core.interest.application;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.model.InterestId;
import com.gomo.app.core.interest.domain.model.Logo;
import com.gomo.app.core.interest.domain.service.InterestService;
import com.gomo.app.support.image.port.DeleteImagePortIn;
import com.gomo.app.support.image.port.UploadImagePortIn;
import com.gomo.app.support.logging.AuditLog;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class UpdateLogoUseCase {

	private final UploadImagePortIn uploadImagePortIn;
	private final DeleteImagePortIn deleteImagePortIn;
	private final InterestService interestService;

	@AuditLog(action = "UPDATE_INTEREST_LOGO")
	public void update(InterestId interestId, MultipartFile updatedLogo) {
		Interest interest = interestService.find(interestId);
		String logoUrl = uploadImagePortIn.upload(updatedLogo).orElse(null);
		deletePreviousLogo(interest);
		interest.updateLogo(Logo.of(logoUrl));
	}

	private void deletePreviousLogo(Interest interest) {
		if (!interest.hasDefaultLogo()) {
			deleteImagePortIn.delete(interest.getLogo().getUrl());
		}
	}
}
