package com.gomo.app.core.interest.application.service;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.logging.AuditLog;
import com.gomo.app.core.interest.application.port.in.CreateInterestRelationUseCase;
import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.model.InterestRelation;
import com.gomo.app.core.interest.domain.service.InterestRelationService;
import com.gomo.app.core.interest.domain.service.InterestService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
class CreateInterestRelationService implements CreateInterestRelationUseCase {

	private final InterestService interestService;
	private final InterestRelationService interestRelationService;

	@AuditLog(action = "CREATE_INTEREST_RELATION")
	public UUID create(UUID registrantId, UUID parentInterestId, UUID childInterestId) {
		Interest parentInterest = interestService.find(parentInterestId);
		Interest childInterest = interestService.find(childInterestId);
		InterestRelation interestRelation = interestRelationService.create(registrantId, parentInterest, childInterest);
		return interestRelation.getId();
	}
}
