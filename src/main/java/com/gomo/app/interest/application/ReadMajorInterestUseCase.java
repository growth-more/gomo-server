package com.gomo.app.interest.application;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.MajorInterest;
import com.gomo.app.interest.domain.model.RegistrantId;
import com.gomo.app.interest.domain.repository.InterestRepository;
import com.gomo.app.interest.domain.repository.MajorInterestRepository;
import com.gomo.app.interest.presentation.response.ListMajorInterestResponse;
import com.gomo.app.interest.presentation.response.ReadMajorInterestResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class ReadMajorInterestUseCase {

	private final MajorInterestRepository majorInterestRepository;
	private final InterestRepository interestRepository;

	public ListMajorInterestResponse findAll(UUID accessorId) {
		List<MajorInterest> majorInterests = majorInterestRepository.findAllByRegistrantIdOrderByDisplayOrder(RegistrantId.of(accessorId));
		Map<InterestId, MajorInterest> majorInterestMap = majorInterests.stream()
			.collect((Collectors.toMap(MajorInterest::getInterestId, Function.identity())));

		List<Interest> interests = interestRepository.findAllByIdIsIn(majorInterestMap.keySet());
		List<ReadMajorInterestResponse> readResponses = interests.stream()
			.map(interest -> ReadMajorInterestResponse.of(majorInterestMap.get(interest.getId()), interest))
			.toList();

		return ListMajorInterestResponse.of(readResponses);
	}
}
