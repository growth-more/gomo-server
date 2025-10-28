package com.gomo.app.core.interest.domain.service;

import java.util.UUID;

import org.jetbrains.annotations.NotNull;

import com.gomo.app.common.arch.DomainService;
import com.gomo.app.common.displayorder.DisplayOrder;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.core.interest.domain.exception.MajorInterestDuplicatedException;
import com.gomo.app.core.interest.domain.exception.MajorInterestNotFoundException;
import com.gomo.app.core.interest.domain.exception.code.MajorInterestErrorCode;
import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.model.MajorInterest;
import com.gomo.app.core.interest.domain.repository.MajorInterestRepository;

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

	public MajorInterest find(UUID majorInterestId) {
		return majorInterestRepository.findById(majorInterestId)
			.orElseThrow(() -> new MajorInterestNotFoundException(MajorInterestErrorCode.NOT_FOUND));
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
			UUIDGenerator.generate(),
			interest.getRegistrantId(),
			interest.getId(),
			DisplayOrder.of(maxDisplayOrder + 1)
		);
	}
}
