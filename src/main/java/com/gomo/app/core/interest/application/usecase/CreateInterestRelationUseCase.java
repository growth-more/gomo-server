package com.gomo.app.core.interest.application.usecase;

import java.util.UUID;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.model.InterestRelation;
import com.gomo.app.core.interest.domain.service.InterestRelationService;
import com.gomo.app.core.interest.domain.service.InterestService;
import com.gomo.app.support.logging.AuditLog;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class CreateInterestRelationUseCase {

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
