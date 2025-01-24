package com.gomo.app.interest.application;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.repository.InterestRepository;
import com.gomo.app.interest.presentation.request.LogoUpdateInterestRequest;
import com.gomo.app.interest.presentation.request.UpdateInterestRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class UpdateInterestUseCase {

	private final InterestRepository interestRepository;

	public void update(InterestId interestId, UpdateInterestRequest request) {

	}

	public void updateLogo(InterestId interestId, LogoUpdateInterestRequest request) {

	}
}
