package com.gomo.app.interest.application;

import java.util.UUID;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.interest.domain.model.MajorInterest;
import com.gomo.app.interest.domain.model.MajorInterestId;
import com.gomo.app.interest.domain.repository.MajorInterestRepository;
import com.gomo.app.interest.domain.service.MajorInterestService;
import com.gomo.app.logging.AuditLog;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class DeleteMajorInterestUseCase {

	private final MajorInterestService majorInterestService;
	private final MajorInterestRepository majorInterestRepository;

	@AuditLog(action = "DELETE_MAJOR_INTEREST")
	public void delete(UUID registrantId, UUID majorInterestId) {
		MajorInterest majorInterest = majorInterestService.find(MajorInterestId.of(majorInterestId));
		majorInterest.validateAuthority(registrantId);
		majorInterestRepository.delete(majorInterest);
	}
}
