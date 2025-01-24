package com.gomo.app.interest.application;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.common.domain.OrderChanger;
import com.gomo.app.interest.domain.repository.MajorInterestRepository;
import com.gomo.app.interest.presentation.request.OrderUpdateMajorInterestRequest;
import com.gomo.app.member.domain.model.MemberId;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class OrderUpdateMajorInterestUseCase {

	private final MajorInterestRepository majorInterestRepository;
	private final OrderChanger orderChanger;

	public void update(MemberId memberId, OrderUpdateMajorInterestRequest request) {

	}
}
