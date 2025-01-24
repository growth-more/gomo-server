package com.gomo.app.interest.application;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.interest.domain.repository.InterestRepository;
import com.gomo.app.interest.presentation.response.ListMajorInterestResponse;
import com.gomo.app.member.domain.model.MemberId;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class ReadMajorInterestUseCase {

	private final InterestRepository interestRepository;

	public ListMajorInterestResponse findAll(MemberId memberId) {
		return null;
	}
}
