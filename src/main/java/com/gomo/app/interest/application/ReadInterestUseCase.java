package com.gomo.app.interest.application;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.MajorInterest;
import com.gomo.app.interest.domain.model.RegistrantId;
import com.gomo.app.interest.domain.repository.InterestRepository;
import com.gomo.app.interest.domain.repository.MajorInterestRepository;
import com.gomo.app.interest.domain.service.InterestService;
import com.gomo.app.interest.presentation.response.ListInterestResponse;
import com.gomo.app.interest.presentation.response.ReadInterestResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class ReadInterestUseCase {

	private final InterestService interestService;
	private final InterestRepository interestRepository;
	private final MajorInterestRepository majorInterestRepository;

	public ReadInterestResponse find(InterestId interestId) {
		Interest interest = interestService.find(interestId);
		UUID majorInterestId = majorInterestRepository.findByInterestId(interestId)
			.map(MajorInterest::uuid)
			.orElse(null);
		return ReadInterestResponse.of(interest, majorInterestId);
	}

	public ListInterestResponse findAll(RegistrantId registrantId) {
		List<Interest> interests = interestRepository.findAllByRegistrantId(registrantId);
		List<InterestId> interestIds = interests.stream().map(Interest::getId).toList();
		Map<UUID, UUID> majorInterestMap = majorInterestRepository.findAllByRegistrantIdAndInterestIdIn(registrantId, interestIds).stream()
			.collect(Collectors.toMap(MajorInterest::interestUuid, MajorInterest::uuid));

		List<ReadInterestResponse> interestResponses = interests.stream().map(interest -> {
			UUID majorInterestId = majorInterestMap.get(interest.uuid());
			return ReadInterestResponse.of(interest, majorInterestId);
		}).toList();
		return ListInterestResponse.of(interestResponses);
	}
}
