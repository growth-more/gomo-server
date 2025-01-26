package com.gomo.app.interest.application;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.common.exception.DomainErrorCode;
import com.gomo.app.common.exception.NotFoundException;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.repository.InterestRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class DeleteInterestUseCase {

	private final InterestRepository interestRepository;

	public void delete(UUID registrantId, InterestId interestId) {
		Interest interest = interestRepository.findById(interestId)
			.orElseThrow(() -> new NotFoundException(DomainErrorCode.NOT_FOUND, "Interest not found with id: " + interestId));
		interest.validateAuthority(registrantId);
		interestRepository.delete(interest);
	}
}
