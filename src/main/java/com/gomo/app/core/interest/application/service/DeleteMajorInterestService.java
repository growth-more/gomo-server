package com.gomo.app.core.interest.application.service;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.logging.AuditLog;
import com.gomo.app.core.interest.application.port.in.DeleteMajorInterestUseCase;
import com.gomo.app.core.interest.domain.model.MajorInterest;
import com.gomo.app.core.interest.domain.repository.MajorInterestRepository;
import com.gomo.app.core.interest.domain.service.MajorInterestService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
class DeleteMajorInterestService implements DeleteMajorInterestUseCase {

	private final MajorInterestService majorInterestService;
	private final MajorInterestRepository majorInterestRepository;

	@AuditLog(action = "DELETE_MAJOR_INTEREST")
	public void delete(UUID registrantId, UUID majorInterestId) {
		MajorInterest majorInterest = majorInterestService.find(majorInterestId);
		majorInterest.validateAuthority(registrantId);
		majorInterestRepository.delete(majorInterest);
	}
}
