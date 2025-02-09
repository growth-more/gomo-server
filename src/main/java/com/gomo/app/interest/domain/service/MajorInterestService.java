package com.gomo.app.interest.domain.service;

import org.jetbrains.annotations.NotNull;

import com.gomo.app.common.domain.service.DisplayOrder;
import com.gomo.app.common.domain.service.DomainService;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.MajorInterest;
import com.gomo.app.interest.domain.model.MajorInterestId;
import com.gomo.app.interest.domain.repository.MajorInterestRepository;
import com.gomo.app.interest.exception.MajorInterestDuplicatedException;
import com.gomo.app.interest.exception.MajorInterestErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class MajorInterestService {

	private final MajorInterestRepository majorInterestRepository;

	public MajorInterest create(Interest interest) {
		ensureNotDuplicated(interest);

		int maxDisplayOrder = majorInterestRepository.findMaxDisplayOrder(interest.getRegistrantId());
		return majorInterestRepository.save(createMajorInterest(interest, maxDisplayOrder));
	}

	private void ensureNotDuplicated(Interest interest) {
		majorInterestRepository.findByInterestId(interest.getId())
			.ifPresent(exists -> {
				throw new MajorInterestDuplicatedException(MajorInterestErrorCode.DUPLICATED);
			});
	}

	@NotNull
	private static MajorInterest createMajorInterest(Interest interest, int maxDisplayOrder) {
		return MajorInterest.of(
			MajorInterestId.of(UUIDGenerator.generate()),
			interest.getRegistrantId(),
			interest.getId(),
			DisplayOrder.of(maxDisplayOrder + 1)
		);
	}
}
