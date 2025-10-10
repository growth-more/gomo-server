package com.gomo.app.core.interest.application.usecase;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.core.interest.application.port.AdjustProficiencyPortIn;
import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.model.InterestId;
import com.gomo.app.core.interest.domain.service.InterestService;
import com.gomo.app.core.interest.domain.service.ProficiencyService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
class AdjustProficiencyUseCase implements AdjustProficiencyPortIn {

	private final InterestService interestService;
	private final ProficiencyService proficiencyService;

	@Override
	public void adjust(UUID interestId, int deltaScore) {
		Interest interest = interestService.find(InterestId.of(interestId));
		proficiencyService.adjust(interest, deltaScore);
	}
}
