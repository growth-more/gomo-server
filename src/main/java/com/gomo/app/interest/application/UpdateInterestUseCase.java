package com.gomo.app.interest.application;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.InterestName;
import com.gomo.app.interest.domain.service.InterestService;
import com.gomo.app.interest.presentation.request.UpdateInterestRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class UpdateInterestUseCase {

	private final InterestService interestService;

	public void update(UUID registrantId, UUID interestId, UpdateInterestRequest request) {
		Interest interest = interestService.find(InterestId.of(interestId));
		interest.validateAuthority(registrantId);
		interest.updateName(InterestName.of(request.getName()));
		interest.updateColorCode(request.getColorCode());
	}
}
