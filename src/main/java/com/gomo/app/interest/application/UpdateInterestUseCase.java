package com.gomo.app.interest.application;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.InterestName;
import com.gomo.app.interest.domain.repository.InterestRepository;
import com.gomo.app.interest.exception.InterestNotFoundException;
import com.gomo.app.interest.exception.code.InterestErrorCode;
import com.gomo.app.interest.presentation.request.UpdateInterestRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class UpdateInterestUseCase {

	private final InterestRepository interestRepository;

	public void update(UUID registrantId, InterestId interestId, UpdateInterestRequest request) {
		Interest interest = interestRepository.findById(interestId)
			.orElseThrow(() -> new InterestNotFoundException(InterestErrorCode.NOT_FOUND));
		interest.validateAuthority(registrantId);
		interest.updateName(InterestName.of(request.getName()));
		interest.updateColorCode(request.getColorCode());
	}
}
