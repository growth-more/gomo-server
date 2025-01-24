package com.gomo.app.interest.application;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.repository.InterestRepository;
import com.gomo.app.interest.presentation.response.ListInterestResponse;
import com.gomo.app.interest.presentation.response.ReadInterestResponse;
import com.gomo.app.member.domain.model.MemberId;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class ReadInterestUseCase {

	private final InterestRepository interestRepository;

	public ReadInterestResponse find(InterestId interestId) {
		return null;
	}

	public ListInterestResponse findAll(MemberId memberId) {
		return null;
	}
}
