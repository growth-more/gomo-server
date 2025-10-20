package com.gomo.app.core.interest.application.usecase;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.model.MajorInterest;
import com.gomo.app.core.interest.domain.service.InterestService;
import com.gomo.app.core.interest.domain.service.MajorInterestService;
import com.gomo.app.support.logging.AuditLog;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
public class CreateMajorInterestUseCase {

	private final InterestService interestService;
	private final MajorInterestService majorInterestService;

	@AuditLog(action = "CREATE_MAJOR_INTEREST")
	public UUID create(UUID registrantId, UUID interestId) {
		Interest interest = interestService.find(interestId);
		interest.validateAuthority(registrantId);
		MajorInterest majorInterest = majorInterestService.create(interest);
		return majorInterest.getId();
	}
}
