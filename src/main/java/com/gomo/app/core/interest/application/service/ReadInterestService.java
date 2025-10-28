package com.gomo.app.core.interest.application.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.core.interest.application.port.dto.InterestDto;
import com.gomo.app.core.interest.application.port.in.ReadInterestUseCase;
import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.model.MajorInterest;
import com.gomo.app.core.interest.domain.repository.InterestRepository;
import com.gomo.app.core.interest.domain.repository.MajorInterestRepository;
import com.gomo.app.core.interest.domain.service.InterestService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
class ReadInterestService implements ReadInterestUseCase {

	private final InterestService interestService;
	private final InterestRepository interestRepository;
	private final MajorInterestRepository majorInterestRepository;

	// TODO [2025-10-18] jhl221123 : InterestDto 에서 majorInterestId를 제거하고, 두 가지 타입의 DTO를 필요에 따라 제공해야합니다.
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
		Map<UUID, UUID> majorIdsByInterestId = majorInterestRepository.findAllByRegistrantIdAndInterestIdIn(registrantId, interestIds).stream()
			.collect(Collectors.toMap(MajorInterest::getInterestId, MajorInterest::getId));

		return interests.stream().map(interest -> {
			UUID majorInterestId = majorIdsByInterestId.get(interest.getId());
			return InterestDto.of(interest, majorInterestId);
		}).toList();
	}

	@Override
	public List<InterestDto> findAllByRegistrantIds(Set<UUID> registrantIds) {
		List<Interest> interests = interestRepository.findAllByRegistrantIdIn(registrantIds);
		List<UUID> interestIds = interests.stream().map(Interest::getId).toList();
		Map<UUID, UUID> majorIdsByInterestId = majorInterestRepository.findByInterestIdIn(interestIds).stream()
			.collect(Collectors.toMap(MajorInterest::getInterestId, MajorInterest::getId));

		return interests.stream().map(interest -> {
			UUID majorInterestId = majorIdsByInterestId.get(interest.getId());
			return InterestDto.of(interest, majorInterestId);
		}).toList();
	}
}
