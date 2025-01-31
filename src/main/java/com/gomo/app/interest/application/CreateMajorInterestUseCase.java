package com.gomo.app.interest.application;

import static com.gomo.app.common.exception.DomainErrorCode.*;

import java.util.UUID;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.common.domain.service.DisplayOrder;
import com.gomo.app.common.exception.NotFoundException;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.MajorInterest;
import com.gomo.app.interest.domain.model.MajorInterestId;
import com.gomo.app.interest.domain.model.RegistrantId;
import com.gomo.app.interest.domain.repository.InterestRepository;
import com.gomo.app.interest.domain.repository.MajorInterestRepository;
import com.gomo.app.interest.exception.MajorInterestDuplicatedException;
import com.gomo.app.interest.exception.MajorInterestErrorCode;
import com.gomo.app.interest.presentation.response.CreateMajorInterestResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class CreateMajorInterestUseCase {

	private final InterestRepository interestRepository;
	private final MajorInterestRepository majorInterestRepository;

	public CreateMajorInterestResponse create(UUID accessorId, InterestId interestId) {
		Interest interest = interestRepository.findById(interestId)
			.orElseThrow(() -> new NotFoundException(NOT_FOUND, "Interest not found with id: " + interestId.getId()));
		interest.validateAuthority(accessorId);

		majorInterestRepository.findByInterestId(interestId)
			.ifPresent(alreadyExist -> {
				throw new MajorInterestDuplicatedException(MajorInterestErrorCode.DUPLICATED);
			});

		RegistrantId registrantId = RegistrantId.of(accessorId);
		long lastNumber = majorInterestRepository.countAllByRegistrantId(registrantId);
		MajorInterest majorInterest = MajorInterest.of(
			MajorInterestId.of(UUIDGenerator.generate()),
			registrantId,
			interest.getId(),
			DisplayOrder.of((int)(lastNumber + 1))
		);

		MajorInterest savedMajorInterest = majorInterestRepository.save(majorInterest);
		return CreateMajorInterestResponse.of(savedMajorInterest.getId());
	}
}
