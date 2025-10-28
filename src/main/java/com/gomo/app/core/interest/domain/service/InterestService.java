package com.gomo.app.core.interest.domain.service;

import java.util.UUID;

import com.gomo.app.common.arch.DomainService;
import com.gomo.app.core.interest.domain.exception.InterestNotFoundException;
import com.gomo.app.core.interest.domain.exception.code.InterestErrorCode;
import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.repository.InterestRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class InterestService {

	private final InterestRepository interestRepository;

	public Interest find(UUID interestId) {
		return interestRepository.findById(interestId)
			.orElseThrow(() -> new InterestNotFoundException(InterestErrorCode.NOT_FOUND));
	}
}
