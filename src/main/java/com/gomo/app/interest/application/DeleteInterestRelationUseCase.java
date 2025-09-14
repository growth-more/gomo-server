package com.gomo.app.interest.application;

import java.util.UUID;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.interest.domain.model.InterestRelation;
import com.gomo.app.interest.domain.model.InterestRelationId;
import com.gomo.app.interest.domain.service.InterestRelationService;
import com.gomo.app.logging.AuditLog;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class DeleteInterestRelationUseCase {

	private final InterestRelationService interestRelationService;

	@AuditLog(action = "DELETE_INTEREST_RELATION")
	public void delete(UUID accessorId, UUID interestRelationId) {
		InterestRelation interestRelation = interestRelationService.find(InterestRelationId.of(interestRelationId));
		interestRelation.validateAuthority(accessorId);
		interestRelationService.delete(interestRelation);
	}
}
