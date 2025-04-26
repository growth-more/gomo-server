package com.gomo.app.interest.application;

import java.util.UUID;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.MajorInterest;
import com.gomo.app.interest.domain.repository.InterestRepository;
import com.gomo.app.interest.domain.service.MajorInterestService;
import com.gomo.app.interest.exception.InterestNotFoundException;
import com.gomo.app.interest.exception.code.InterestErrorCode;
import com.gomo.app.interest.presentation.response.CreateMajorInterestResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class CreateMajorInterestUseCase {

	private final InterestRepository interestRepository;
	private final MajorInterestService majorInterestService;

	public CreateMajorInterestResponse create(UUID accessorId, InterestId interestId) {
		Interest interest = interestRepository.findById(interestId)
			.orElseThrow(() -> new InterestNotFoundException(InterestErrorCode.NOT_FOUND));
		interest.validateAuthority(accessorId);

		MajorInterest majorInterest = majorInterestService.create(interest);
		return CreateMajorInterestResponse.of(majorInterest.getId());
	}
}
