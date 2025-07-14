package com.gomo.app.interest.domain.service;

import com.gomo.app.common.DomainService;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.repository.InterestRepository;
import com.gomo.app.interest.exception.InterestNotFoundException;
import com.gomo.app.interest.exception.code.InterestErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class InterestService {

	private final InterestRepository interestRepository;

	public Interest find(InterestId interestId) {
		return interestRepository.findById(interestId)
			.orElseThrow(() -> new InterestNotFoundException(InterestErrorCode.NOT_FOUND));
	}
}
