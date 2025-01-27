package com.gomo.app.interest.application;

import static com.gomo.app.common.exception.DomainErrorCode.*;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.common.domain.service.ImageService;
import com.gomo.app.common.exception.NotFoundException;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.repository.InterestRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class UpdateInterestLogoUseCase {

	private final ImageService imageService;
	private final InterestRepository interestRepository;

	public void update(InterestId interestId, MultipartFile updatedLogo) {
		Interest interest = interestRepository.findById(interestId)
			.orElseThrow(() -> new NotFoundException(NOT_FOUND, "Interest not found with id: " + interestId.getId()));
		imageService.deleteImage(interest.getLogoUrl());

		String updatedUrl = imageService.uploadImage(updatedLogo);
		interest.updateLogoUrl(updatedUrl);
	}
}
