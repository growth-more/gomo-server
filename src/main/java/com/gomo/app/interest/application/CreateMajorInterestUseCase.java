package com.gomo.app.interest.application;

import java.util.UUID;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.MajorInterest;
import com.gomo.app.interest.domain.service.InterestService;
import com.gomo.app.interest.domain.service.MajorInterestService;
import com.gomo.app.interest.presentation.response.CreateMajorInterestResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class CreateMajorInterestUseCase {

	private final InterestService interestService;
	private final MajorInterestService majorInterestService;

	public CreateMajorInterestResponse create(UUID accessorId, UUID interestId) {
		Interest interest = interestService.find(InterestId.of(interestId));
		interest.validateAuthority(accessorId);

		MajorInterest majorInterest = majorInterestService.create(interest);
		return CreateMajorInterestResponse.of(majorInterest.uuid());
	}
}
