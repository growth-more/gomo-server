package com.gomo.app.interest.application;

import java.util.UUID;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.interest.domain.model.MajorInterest;
import com.gomo.app.interest.domain.model.MajorInterestId;
import com.gomo.app.interest.domain.repository.MajorInterestRepository;
import com.gomo.app.interest.domain.service.MajorInterestService;
import com.gomo.app.interest.exception.MajorInterestNotFoundException;
import com.gomo.app.interest.exception.code.MajorInterestErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class DeleteMajorInterestUseCase {

	private final MajorInterestService majorInterestService;
	private final MajorInterestRepository majorInterestRepository;

	public void delete(UUID accessorId, MajorInterestId majorInterestId) {
		MajorInterest majorInterest = majorInterestService.find(majorInterestId);
		majorInterest.validateAuthority(accessorId);
		majorInterestRepository.delete(majorInterest);
	}
}
