package com.gomo.app.core.interest.application.service;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.core.interest.application.port.in.AdjustProficiencyUseCase;
import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.service.InterestService;
import com.gomo.app.core.interest.domain.service.ProficiencyService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
class AdjustProficiencyService implements AdjustProficiencyUseCase {

	private final InterestService interestService;
	private final ProficiencyService proficiencyService;

	@Override
	public void adjust(UUID interestId, int deltaScore) {
		Interest interest = interestService.find(interestId);
		proficiencyService.adjust(interest, deltaScore);
	}
}
