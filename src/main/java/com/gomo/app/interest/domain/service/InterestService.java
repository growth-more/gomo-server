package com.gomo.app.interest.domain.service;

import com.gomo.app.common.DomainService;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestQuota;
import com.gomo.app.interest.domain.repository.InterestRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class InterestService {

	private final InterestRepository interestRepository;

	public Interest create(Interest interest, InterestQuota interestQuota) {
		long interestCount = interestRepository.countAllByRegistrantId(interest.getRegistrantId());
		interestQuota.validateCount(interestCount);
		return interestRepository.save(interest);
	}
}
