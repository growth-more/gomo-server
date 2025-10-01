package com.gomo.app.core.interest.application;

import java.util.List;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.model.InterestId;
import com.gomo.app.core.interest.domain.model.InterestRelation;
import com.gomo.app.core.interest.domain.repository.InterestRepository;
import com.gomo.app.core.interest.domain.repository.MajorInterestRepository;
import com.gomo.app.core.interest.domain.service.InterestRelationService;
import com.gomo.app.core.interest.domain.service.InterestService;
import com.gomo.app.support.image.port.DeleteImagePortIn;
import com.gomo.app.support.logging.AuditLog;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class DeleteInterestUseCase {

	private final DeleteImagePortIn deleteImagePortIn;
	private final InterestService interestService;
	private final InterestRelationService interestRelationService;
	private final InterestRepository interestRepository;
	private final MajorInterestRepository majorInterestRepository;

	@AuditLog(action = "DELETE_INTEREST")
	public void delete(UUID registrantId, UUID interestId) {
		Interest interest = interestService.find(InterestId.of(interestId));
		interest.validateAuthority(registrantId);

		deleteLogoUrl(interest);
		deleteMajorInterest(InterestId.of(interestId));
		deleteInterestRelations(interestId);
		interestRepository.delete(interest);
	}

	// TODO <jhl221123>: 관심사를 삭제할 때, 주요관심사와 관계선을 함께 제거하는 것은 도메인 정책임으로 도메인 서비스로 분리해야 합니다.
	private void deleteInterestRelations(UUID interestId) {
		List<InterestRelation> interestRelations = interestRelationService.findAllByInterestId(interestId);
		if (!interestRelations.isEmpty()) {
			for (InterestRelation relation : interestRelations) {
				interestRelationService.delete(relation);
			}
		}
	}

	private void deleteMajorInterest(InterestId interestId) {
		majorInterestRepository.deleteByInterestId(interestId);
	}

	private void deleteLogoUrl(Interest interest) {
		if (!interest.hasDefaultLogo()) {
			deleteImagePortIn.delete(interest.getLogo().getUrl());
		}
	}
}
