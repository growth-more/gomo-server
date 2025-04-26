package com.gomo.app.interest.application;

import java.util.UUID;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.interest.domain.model.MajorInterest;
import com.gomo.app.interest.domain.model.MajorInterestId;
import com.gomo.app.interest.domain.repository.MajorInterestRepository;
import com.gomo.app.interest.exception.MajorInterestNotFoundException;
import com.gomo.app.interest.exception.code.MajorInterestErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class DeleteMajorInterestUseCase {

	private final MajorInterestRepository majorInterestRepository;

	public void delete(UUID accessorId, MajorInterestId majorInterestId) {
		MajorInterest majorInterest = majorInterestRepository.findById(majorInterestId)
			.orElseThrow(() -> new MajorInterestNotFoundException(MajorInterestErrorCode.NOT_FOUND));
		majorInterest.validateAuthority(accessorId);
		majorInterestRepository.delete(majorInterest);
	}
}
