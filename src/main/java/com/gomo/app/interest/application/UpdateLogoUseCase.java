package com.gomo.app.interest.application;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.image.port.DeleteImagePortIn;
import com.gomo.app.interest.application.port.UploadLogoPortOut;
import com.gomo.app.interest.application.port.dto.LogoDto;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.Logo;
import com.gomo.app.interest.domain.service.InterestService;
import com.gomo.app.logging.AuditLog;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class UpdateLogoUseCase {

	private final InterestService interestService;
	private final UploadLogoPortOut uploadLogoPortOut;
	private final DeleteImagePortIn deleteImagePortIn;

	@AuditLog(action = "UPDATE_INTEREST_LOGO")
	public void update(InterestId interestId, MultipartFile updatedLogo) {
		Interest interest = interestService.find(interestId);
		LogoDto logoDto = uploadLogoPortOut.upload(updatedLogo);
		deletePreviousLogo(interest);
		interest.updateLogo(Logo.of(logoDto.url()));
	}

	private void deletePreviousLogo(Interest interest) {
		if (!interest.hasDefaultLogo()) {
			deleteImagePortIn.delete(interest.getLogo().getUrl());
		}
	}
}
