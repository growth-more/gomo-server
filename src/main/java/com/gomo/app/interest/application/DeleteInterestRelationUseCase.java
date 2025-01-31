package com.gomo.app.interest.application;

import static com.gomo.app.common.exception.DomainErrorCode.*;

import java.util.UUID;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.common.exception.NotFoundException;
import com.gomo.app.interest.domain.model.InterestRelation;
import com.gomo.app.interest.domain.model.InterestRelationId;
import com.gomo.app.interest.domain.repository.InterestRelationRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class DeleteInterestRelationUseCase {

	private final InterestRelationRepository interestRelationRepository;

	public void delete(UUID accessorId, InterestRelationId interestRelationId) {
		InterestRelation interestRelation = interestRelationRepository.findById(interestRelationId)
			.orElseThrow(() -> new NotFoundException(NOT_FOUND, "interest relation not found with id " + interestRelationId));
		interestRelation.validateAuthority(accessorId);
		interestRelationRepository.delete(interestRelation);
	}
}
