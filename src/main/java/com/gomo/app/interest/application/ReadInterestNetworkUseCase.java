package com.gomo.app.interest.application;

import java.util.List;
import java.util.UUID;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.interest.domain.model.RegistrantId;
import com.gomo.app.interest.domain.repository.InterestRelationRepository;
import com.gomo.app.interest.domain.repository.InterestRepository;
import com.gomo.app.interest.presentation.response.InterestNetworkResponse;
import com.gomo.app.interest.presentation.response.ReadInterestRelationResponse;
import com.gomo.app.interest.presentation.response.ReadInterestResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class ReadInterestNetworkUseCase {

	private final InterestRepository interestRepository;
	private final InterestRelationRepository interestRelationRepository;

	public InterestNetworkResponse find(UUID accessorId) {
		RegistrantId registrantId = RegistrantId.of(accessorId);
		List<ReadInterestResponse> interests = interestRepository.findAllByRegistrantId(registrantId).stream()
			.map(ReadInterestResponse::of)
			.toList();

		List<ReadInterestRelationResponse> relations = interestRelationRepository.findAllByRegistrantId(registrantId).stream()
			.map(ReadInterestRelationResponse::of)
			.toList();
		return InterestNetworkResponse.of(interests, relations);
	}
}
