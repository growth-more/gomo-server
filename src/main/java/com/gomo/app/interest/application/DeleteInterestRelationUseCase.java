package com.gomo.app.interest.application;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.interest.domain.model.InterestRelationId;
import com.gomo.app.interest.domain.model.RegistrantId;
import com.gomo.app.interest.domain.repository.InterestRelationRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class DeleteInterestRelationUseCase {

	private final InterestRelationRepository interestRelationRepository;

	public void delete(RegistrantId registrantId, InterestRelationId interestRelationId) {

	}
}
