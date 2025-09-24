package com.gomo.app.interest.application;

import java.util.UUID;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.interest.application.port.dto.CreateInterestRelationDto;
import com.gomo.app.interest.domain.model.ChildInterestId;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.InterestRelation;
import com.gomo.app.interest.domain.model.ParentInterestId;
import com.gomo.app.interest.domain.model.RegistrantId;
import com.gomo.app.interest.domain.service.InterestRelationService;
import com.gomo.app.logging.AuditLog;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class CreateInterestRelationUseCase {

	private final InterestRelationService interestRelationService;

	@AuditLog(action = "CREATE_INTEREST_RELATION")
	public CreateInterestRelationDto create(UUID registrantId, UUID parentInterestId, UUID childInterestId) {
		InterestRelation interestRelation = interestRelationService.create(
			RegistrantId.of(registrantId),
			ParentInterestId.of(InterestId.of(parentInterestId)),
			ChildInterestId.of(InterestId.of(childInterestId))
		);
		return CreateInterestRelationDto.of(interestRelation.uuid());
	}
}
