package com.gomo.app.interest.application;

import java.util.UUID;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.interest.domain.model.InterestRelation;
import com.gomo.app.interest.domain.model.InterestRelationId;
import com.gomo.app.interest.domain.repository.InterestRelationRepository;
import com.gomo.app.interest.exception.InterestRelationNotFoundException;
import com.gomo.app.interest.exception.code.InterestRelationErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class DeleteInterestRelationUseCase {

	private final InterestRelationRepository interestRelationRepository;

	public void delete(UUID accessorId, InterestRelationId interestRelationId) {
		InterestRelation interestRelation = interestRelationRepository.findById(interestRelationId)
			.orElseThrow(() -> new InterestRelationNotFoundException(InterestRelationErrorCode.NOT_FOUND));
		interestRelation.validateAuthority(accessorId);
		interestRelationRepository.delete(interestRelation);
	}
}
