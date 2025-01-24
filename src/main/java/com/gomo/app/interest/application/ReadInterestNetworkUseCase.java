package com.gomo.app.interest.application;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.interest.domain.repository.InterestRelationRepository;
import com.gomo.app.interest.domain.repository.InterestRepository;
import com.gomo.app.interest.presentation.response.InterestNetworkResponse;
import com.gomo.app.member.domain.model.MemberId;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class ReadInterestNetworkUseCase {

	private final InterestRepository interestRepository;
	private final InterestRelationRepository interestRelationRepository;

	public InterestNetworkResponse find(MemberId memberId) {
		return null;
	}
}
