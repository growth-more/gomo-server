package com.gomo.app.core.interest.application.usecase;

import java.util.UUID;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.model.InterestRelation;
import com.gomo.app.core.interest.domain.model.InterestRelationId;
import com.gomo.app.core.interest.domain.service.InterestRelationService;
import com.gomo.app.core.interest.domain.service.InterestService;
import com.gomo.app.support.logging.AuditLog;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class DeleteInterestRelationUseCase {

	private final InterestService interestService;
	private final InterestRelationService interestRelationService;

	@AuditLog(action = "DELETE_INTEREST_RELATION")
	public void delete(UUID registrantId, UUID interestRelationId) {
		InterestRelation interestRelation = interestRelationService.find(InterestRelationId.of(interestRelationId));
		interestRelation.validateAuthority(registrantId);
		Interest parentInterest = interestService.find(interestRelation.getParentInterestId().toInterestId());
		Interest childInterest = interestService.find(interestRelation.getChildInterestId().toInterestId());
		interestRelationService.delete(interestRelation, parentInterest, childInterest);
	}
}
