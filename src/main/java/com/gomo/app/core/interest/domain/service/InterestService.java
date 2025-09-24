package com.gomo.app.core.interest.domain.service;

import com.gomo.app.common.DomainService;
import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.model.InterestId;
import com.gomo.app.core.interest.domain.repository.InterestRepository;
import com.gomo.app.core.interest.exception.InterestNotFoundException;
import com.gomo.app.core.interest.exception.code.InterestErrorCode;

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
