package com.gomo.app.core.interest.application.usecase;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.core.interest.application.port.dto.MajorInterestDto;
import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.model.MajorInterest;
import com.gomo.app.core.interest.domain.repository.InterestRepository;
import com.gomo.app.core.interest.domain.repository.MajorInterestRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class ReadMajorInterestUseCase {

	private final MajorInterestRepository majorInterestRepository;
	private final InterestRepository interestRepository;

	public List<MajorInterestDto> findAll(UUID registrantId) {
		List<MajorInterest> majorInterests = majorInterestRepository.findAllByRegistrantIdOrderByDisplayOrder(registrantId);
		Map<UUID, MajorInterest> majorInterestMap = majorInterests.stream()
			.collect((Collectors.toMap(MajorInterest::getInterestId, Function.identity())));

		List<Interest> interests = interestRepository.findAllByIdIsIn(majorInterestMap.keySet());
		return interests.stream()
			.map(interest -> MajorInterestDto.from(majorInterestMap.get(interest.getId()), interest))
			.toList();
	}
}
