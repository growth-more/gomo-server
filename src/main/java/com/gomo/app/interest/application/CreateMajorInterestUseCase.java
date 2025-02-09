package com.gomo.app.interest.application;

import static com.gomo.app.common.exception.DomainErrorCode.*;

import java.util.UUID;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.common.exception.NotFoundException;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.MajorInterest;
import com.gomo.app.interest.domain.repository.InterestRepository;
import com.gomo.app.interest.domain.service.MajorInterestService;
import com.gomo.app.interest.presentation.response.CreateMajorInterestResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class CreateMajorInterestUseCase {

	private final InterestRepository interestRepository;
	private final MajorInterestService majorInterestService;

	public CreateMajorInterestResponse create(UUID accessorId, InterestId interestId) {
		Interest interest = interestRepository.findById(interestId)
			.orElseThrow(() -> new NotFoundException(NOT_FOUND, "Interest not found with id: " + interestId.getId()));
		interest.validateAuthority(accessorId);

		MajorInterest majorInterest = majorInterestService.create(interest);
		return CreateMajorInterestResponse.of(majorInterest.getId());
	}
}
