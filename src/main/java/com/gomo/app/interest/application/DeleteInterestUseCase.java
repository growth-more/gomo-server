package com.gomo.app.interest.application;

import java.util.List;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.image.ImageService;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.InterestRelation;
import com.gomo.app.interest.domain.repository.InterestRepository;
import com.gomo.app.interest.domain.repository.MajorInterestRepository;
import com.gomo.app.interest.domain.service.InterestRelationService;
import com.gomo.app.interest.domain.service.InterestService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class DeleteInterestUseCase {

	private final InterestService interestService;
	private final ImageService imageService;
	private final InterestRelationService interestRelationService;
	private final InterestRepository interestRepository;
	private final MajorInterestRepository majorInterestRepository;

	public void delete(UUID registrantId, UUID interestId) {
		Interest interest = interestService.find(InterestId.of(interestId));
		interest.validateAuthority(registrantId);

		deleteLogoUrl(interest);
		deleteMajorInterest(InterestId.of(interestId));
		deleteInterestRelations(interestId);
		interestRepository.delete(interest);
	}

	// TODO <jhl221123>: InterestRelationService로 분리해야 합니다.
	private void deleteInterestRelations(UUID interestId) {
		List<InterestRelation> interestRelations = interestRelationService.findAllByInterestId(interestId);

		if(!interestRelations.isEmpty()) {
			for(InterestRelation relation : interestRelations) {
				interestRelationService.delete(relation);
			}
		}
	}

	private void deleteMajorInterest(InterestId interestId) {
		majorInterestRepository.deleteByInterestId(interestId);
	}

	private void deleteLogoUrl(Interest interest) {
		if(!interest.hasDefaultLogo()) {
			imageService.deleteImage(interest.getLogo().getUrl());
		}
	}
}
