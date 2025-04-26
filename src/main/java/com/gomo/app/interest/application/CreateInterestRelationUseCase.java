package com.gomo.app.interest.application;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.interest.domain.model.ChildInterestId;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.InterestRelation;
import com.gomo.app.interest.domain.model.ParentInterestId;
import com.gomo.app.interest.domain.model.RegistrantId;
import com.gomo.app.interest.domain.service.InterestRelationService;
import com.gomo.app.interest.presentation.request.CreateInterestRelationRequest;
import com.gomo.app.interest.presentation.response.CreateInterestRelationResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class CreateInterestRelationUseCase {

	private final InterestRelationService interestRelationService;

	public CreateInterestRelationResponse create(RegistrantId registrantId, CreateInterestRelationRequest request) {
		InterestRelation interestRelation = interestRelationService.create(
			registrantId,
			ParentInterestId.of(InterestId.of(request.getParentInterestId())),
			ChildInterestId.of(InterestId.of(request.getChildInterestId()))
		);

		return CreateInterestRelationResponse.of(interestRelation.getId());
	}
}
