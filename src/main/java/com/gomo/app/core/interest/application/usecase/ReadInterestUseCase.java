package com.gomo.app.core.interest.application.usecase;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.core.interest.application.port.ReadInterestPortIn;
import com.gomo.app.core.interest.application.port.dto.InterestDto;
import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.model.MajorInterest;
import com.gomo.app.core.interest.domain.repository.InterestRepository;
import com.gomo.app.core.interest.domain.repository.MajorInterestRepository;
import com.gomo.app.core.interest.domain.service.InterestService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
class ReadInterestUseCase implements ReadInterestPortIn {

	private final InterestService interestService;
	private final InterestRepository interestRepository;
	private final MajorInterestRepository majorInterestRepository;

	@Override
	public InterestDto find(UUID interestId) {
		Interest interest = interestService.find(interestId);
		UUID majorInterestId = majorInterestRepository.findByInterestId(interestId)
			.map(MajorInterest::getId)
			.orElse(null);
		return InterestDto.of(interest, majorInterestId);
	}

	@Override
	public List<InterestDto> findAll(UUID registrantId) {
		List<Interest> interests = interestRepository.findAllByRegistrantId(registrantId);
		List<UUID> interestIds = interests.stream().map(Interest::getId).toList();
		Map<UUID, UUID> majorInterestMap = majorInterestRepository.findAllByRegistrantIdAndInterestIdIn(registrantId, interestIds).stream()
			.collect(Collectors.toMap(MajorInterest::getInterestId, MajorInterest::getId));

		return interests.stream().map(interest -> {
			UUID majorInterestId = majorInterestMap.get(interest.getId());
			return InterestDto.of(interest, majorInterestId);
		}).toList();
	}
}
