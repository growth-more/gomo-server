package com.gomo.app.core.interest.application.service;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.logging.AuditLog;
import com.gomo.app.core.interest.application.port.in.UpdateLogoUseCase;
import com.gomo.app.core.interest.application.port.out.DeleteLogoPort;
import com.gomo.app.core.interest.application.port.out.UploadLogoPort;
import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.model.Logo;
import com.gomo.app.core.interest.domain.service.InterestService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
class UpdateLogoService implements UpdateLogoUseCase {

	private final UploadLogoPort uploadLogoPort;
	private final DeleteLogoPort deleteLogoPort;
	private final InterestService interestService;

	@AuditLog(action = "UPDATE_INTEREST_LOGO")
	public void update(UUID interestId, MultipartFile updatedLogo) {
		Interest interest = interestService.find(interestId);
		String logoUrl = uploadLogoPort.upload(updatedLogo).orElse(null);
		deletePreviousLogo(interest);
		interest.updateLogo(Logo.of(logoUrl));
	}

	private void deletePreviousLogo(Interest interest) {
		if (!interest.hasDefaultLogo()) {
			deleteLogoPort.delete(interest.getLogo().getUrl());
		}
	}
}
