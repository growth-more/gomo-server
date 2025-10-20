package com.gomo.app.core.interest.application.usecase;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.core.interest.domain.model.MajorInterest;
import com.gomo.app.core.interest.domain.repository.MajorInterestRepository;
import com.gomo.app.core.interest.domain.service.MajorInterestService;
import com.gomo.app.support.logging.AuditLog;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
public class DeleteMajorInterestUseCase {

	private final MajorInterestService majorInterestService;
	private final MajorInterestRepository majorInterestRepository;

	@AuditLog(action = "DELETE_MAJOR_INTEREST")
	public void delete(UUID registrantId, UUID majorInterestId) {
		MajorInterest majorInterest = majorInterestService.find(majorInterestId);
		majorInterest.validateAuthority(registrantId);
		majorInterestRepository.delete(majorInterest);
	}
}
