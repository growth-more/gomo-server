package com.gomo.app.core.interest.application.service;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.logging.AuditLog;
import com.gomo.app.core.interest.application.port.in.DeleteInterestRelationUseCase;
import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.model.InterestRelation;
import com.gomo.app.core.interest.domain.service.InterestRelationService;
import com.gomo.app.core.interest.domain.service.InterestService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
class DeleteInterestRelationService implements DeleteInterestRelationUseCase {

	private final InterestService interestService;
	private final InterestRelationService interestRelationService;

	@AuditLog(action = "DELETE_INTEREST_RELATION")
	public void delete(UUID registrantId, UUID interestRelationId) {
		InterestRelation interestRelation = interestRelationService.find(interestRelationId);
		interestRelation.validateAuthority(registrantId);
		Interest parentInterest = interestService.find(interestRelation.getParentInterestId());
		Interest childInterest = interestService.find(interestRelation.getChildInterestId());
		interestRelationService.delete(interestRelation, parentInterest, childInterest);
	}
}
