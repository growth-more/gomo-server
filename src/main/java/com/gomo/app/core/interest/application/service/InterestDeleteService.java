package com.gomo.app.core.interest.application.service;

import java.util.List;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.logging.AuditLog;
import com.gomo.app.core.interest.application.port.in.InterestDeleter;
import com.gomo.app.core.interest.application.port.out.LogoDeleter;
import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.model.InterestRelation;
import com.gomo.app.core.interest.domain.repository.InterestRepository;
import com.gomo.app.core.interest.domain.repository.MajorInterestRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
class InterestDeleteService implements InterestDeleter {

	private final LogoDeleter logoDeleter;
	private final MajorInterestRepository majorInterestRepository;
	private final InterestRelationService interestRelationService;
	private final InterestService interestService;
	private final InterestRepository interestRepository;

	@AuditLog(action = "DELETE_INTEREST")
	public void delete(UUID registrantId, UUID interestId) {
		Interest interest = interestService.readById(interestId);
		interest.validateAuthority(registrantId);

		deleteLogoUrl(interest);
		deleteMajorInterest(interestId);
		deleteInterestRelations(registrantId, interestId);
		interestRepository.delete(interest);
	}

	private void deleteLogoUrl(Interest interest) {
		if (!interest.hasDefaultLogo()) {
			logoDeleter.delete(interest.getLogo().getUrl());
		}
	}

	private void deleteMajorInterest(UUID interestId) {
		majorInterestRepository.deleteByInterestId(interestId);
	}

	private void deleteInterestRelations(UUID registrantId, UUID interestId) {
		List<InterestRelation> interestRelations = interestRelationService.readAllByInterestId(interestId);
		if (!interestRelations.isEmpty()) {
			for (InterestRelation relation : interestRelations) {
				interestRelationService.delete(registrantId, relation.getId());
			}
		}
	}
}
