package com.gomo.app.interest.application;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.interest.domain.model.ChildInterestId;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.InterestRelation;
import com.gomo.app.interest.domain.model.InterestRelationId;
import com.gomo.app.interest.domain.model.ParentInterestId;
import com.gomo.app.interest.domain.model.RegistrantId;
import com.gomo.app.interest.domain.repository.InterestRelationRepository;
import com.gomo.app.interest.presentation.request.CreateInterestRelationRequest;
import com.gomo.app.interest.presentation.response.CreateInterestRelationResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class CreateInterestRelationUseCase {

	private final InterestRelationRepository interestRelationRepository;

	public CreateInterestRelationResponse create(RegistrantId registrantId, CreateInterestRelationRequest request) {
		InterestRelation interestRelation = InterestRelation.of(
			InterestRelationId.of(UUIDGenerator.generate()),
			registrantId,
			ParentInterestId.of(InterestId.of(request.getParentInterestId())),
			ChildInterestId.of(InterestId.of(request.getChildInterestId()))
		);

		InterestRelation savedRelation = interestRelationRepository.save(interestRelation);
		return CreateInterestRelationResponse.of(savedRelation.getId());
	}
}
