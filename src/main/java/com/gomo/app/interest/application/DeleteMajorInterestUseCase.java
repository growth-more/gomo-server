package com.gomo.app.interest.application;

import java.util.UUID;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.common.exception.DomainErrorCode;
import com.gomo.app.common.exception.NotFoundException;
import com.gomo.app.interest.domain.model.MajorInterest;
import com.gomo.app.interest.domain.model.MajorInterestId;
import com.gomo.app.interest.domain.repository.MajorInterestRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class DeleteMajorInterestUseCase {

	private final MajorInterestRepository majorInterestRepository;

	public void delete(UUID accessorId, MajorInterestId majorInterestId) {
		MajorInterest majorInterest = majorInterestRepository.findById(majorInterestId)
			.orElseThrow(() -> new NotFoundException(DomainErrorCode.NOT_FOUND, "Major interest not found"));
		majorInterest.validateAuthority(accessorId);
		majorInterestRepository.delete(majorInterest);
	}
}
