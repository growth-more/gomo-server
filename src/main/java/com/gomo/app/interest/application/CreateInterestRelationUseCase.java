package com.gomo.app.interest.application;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.interest.domain.repository.InterestRelationRepository;
import com.gomo.app.interest.domain.repository.InterestRepository;
import com.gomo.app.interest.presentation.request.CreateInterestRelationRequest;
import com.gomo.app.interest.presentation.response.CreateInterestRelationResponse;
import com.gomo.app.member.domain.model.MemberId;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class CreateInterestRelationUseCase {

	private final InterestRepository interestRepository;
	private final InterestRelationRepository interestRelationRepository;

	public CreateInterestRelationResponse create(MemberId memberId, CreateInterestRelationRequest request) {
		return null;
	}
}
