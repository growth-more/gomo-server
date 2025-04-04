package com.gomo.app.interest.application;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.common.util.JsonParser;
import com.gomo.app.interest.domain.model.RegistrantId;
import com.gomo.app.interest.domain.repository.InterestRelationRepository;
import com.gomo.app.interest.domain.repository.InterestRepository;
import com.gomo.app.interest.domain.repository.MajorInterestRepository;
import com.gomo.app.interest.presentation.response.InterestNetworkResponse;
import com.gomo.app.interest.presentation.response.ReadInterestRelationResponse;
import com.gomo.app.interest.presentation.response.ReadInterestResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class ReadInterestNetworkUseCase {

	private final InterestRepository interestRepository;
	private final MajorInterestRepository majorInterestRepository;
	private final InterestRelationRepository interestRelationRepository;

	public InterestNetworkResponse find(UUID accessorId) {
		RegistrantId registrantId = RegistrantId.of(accessorId);
		List<ReadInterestResponse> interests = interestRepository.findAllByRegistrantId(registrantId).stream()
			.map(ReadInterestResponse::of)
			.toList();
		markMajorInterests(interests);

		List<ReadInterestRelationResponse> relations = interestRelationRepository.findAllByRegistrantId(registrantId).stream()
			.map(ReadInterestRelationResponse::of)
			.toList();
		return InterestNetworkResponse.of(interests, relations);
	}

	private void markMajorInterests(List<ReadInterestResponse> interestResponses) {
		String interestIdsJson = JsonParser.toJson(interestResponses.stream().map(ReadInterestResponse::getId).toList());
		List<Long> isMajorInterests = majorInterestRepository.existsAsMajorInterests(interestIdsJson);
		IntStream.range(0, interestResponses.size())
			.filter(i -> isMajorInterests.get(i) == 1)
			.forEach(i -> interestResponses.get(i).updateMajorInterest());
	}
}
