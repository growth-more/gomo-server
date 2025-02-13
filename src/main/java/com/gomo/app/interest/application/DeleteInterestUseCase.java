package com.gomo.app.interest.application;

import java.util.List;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.common.domain.service.ImageService;
import com.gomo.app.common.exception.DomainErrorCode;
import com.gomo.app.common.exception.NotFoundException;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.InterestRelation;
import com.gomo.app.interest.domain.repository.InterestRelationRepository;
import com.gomo.app.interest.domain.repository.InterestRepository;
import com.gomo.app.interest.domain.repository.MajorInterestRepository;
import com.gomo.app.interest.domain.service.InterestRelationService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class DeleteInterestUseCase {

	private final ImageService imageService;
	private final InterestRelationService interestRelationService;
	private final InterestRepository interestRepository;
	private final MajorInterestRepository majorInterestRepository;
	private final InterestRelationRepository interestRelationRepository;

	public void delete(UUID registrantId, InterestId interestId) {
		Interest interest = interestRepository.findById(interestId)
			.orElseThrow(() -> new NotFoundException(DomainErrorCode.NOT_FOUND, "Interest not found with id: " + interestId));
		interest.validateAuthority(registrantId);

		deleteLogoUrl(interest);
		deleteMajorInterest(interestId);
		deleteInterestRelations(registrantId, interestId);
		interestRepository.delete(interest);
	}

	private void deleteInterestRelations(UUID registrantId, InterestId interestId) {
		List<InterestRelation> interestRelations = interestRelationRepository.findByInterestId(interestId.getId());

		if(!interestRelations.isEmpty()) {
			for(InterestRelation relation : interestRelations) {
				interestRelationService.delete(registrantId, relation.getId());
			}
		}
	}

	private void deleteMajorInterest(InterestId interestId) {
		majorInterestRepository.deleteByInterestId(interestId);
	}

	private void deleteLogoUrl(Interest interest) {
		if(!interest.hasDefaultLogo()) {
			imageService.deleteImage(interest.getLogoUrl());
		}
	}
}
