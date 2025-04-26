package com.gomo.app.interest.application;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.image.ImageService;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.Logo;
import com.gomo.app.interest.domain.repository.InterestRepository;
import com.gomo.app.interest.exception.InterestNotFoundException;
import com.gomo.app.interest.exception.code.InterestErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class UpdateLogoUseCase {

	private final ImageService imageService;
	private final InterestRepository interestRepository;

	public void update(InterestId interestId, MultipartFile updatedLogo) {
		Interest interest = interestRepository.findById(interestId)
			.orElseThrow(() -> new InterestNotFoundException(InterestErrorCode.NOT_FOUND));

		deletePriorLogo(interest);
		String updatedUrl = imageService.uploadImage(updatedLogo);
		interest.updateLogo(Logo.of(updatedUrl));
	}

	private void deletePriorLogo(Interest interest) {
		if(!interest.hasDefaultLogo()) {
			imageService.deleteImage(interest.getLogo().getUrl());
		}
	}
}
