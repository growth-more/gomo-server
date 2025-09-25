package com.gomo.app.core.interest.application;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.core.interest.application.port.dto.InterestDto;
import com.gomo.app.core.interest.application.port.dto.InterestNetworkDto;
import com.gomo.app.core.interest.application.port.dto.InterestRelationDto;
import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.model.InterestId;
import com.gomo.app.core.interest.domain.model.MajorInterest;
import com.gomo.app.core.interest.domain.model.RegistrantId;
import com.gomo.app.core.interest.domain.repository.InterestRelationRepository;
import com.gomo.app.core.interest.domain.repository.InterestRepository;
import com.gomo.app.core.interest.domain.repository.MajorInterestRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class ReadInterestNetworkUseCase {

	private final InterestRepository interestRepository;
	private final MajorInterestRepository majorInterestRepository;
	private final InterestRelationRepository interestRelationRepository;

	public InterestNetworkDto find(UUID registrantId) {
		RegistrantId targetId = RegistrantId.of(registrantId);
		List<InterestDto> interestDtos = getInterestDtos(targetId);
		List<InterestRelationDto> relationDtos = getRelationDtos(targetId);
		return InterestNetworkDto.of(interestDtos, relationDtos);
	}

	@NotNull
	private List<InterestDto> getInterestDtos(RegistrantId registrantId) {
		List<Interest> interests = interestRepository.findAllByRegistrantId(registrantId);
		List<InterestId> interestIds = interests.stream().map(Interest::getId).toList();
		Map<UUID, UUID> majorInterestMap = majorInterestRepository.findAllByRegistrantIdAndInterestIdIn(registrantId, interestIds).stream()
			.collect(Collectors.toMap(MajorInterest::interestUuid, MajorInterest::uuid));

		return interests.stream().map(interest -> {
			UUID majorInterestId = majorInterestMap.get(interest.uuid());
			return InterestDto.of(interest, majorInterestId);
		}).toList();
	}

	@NotNull
	private List<InterestRelationDto> getRelationDtos(RegistrantId id) {
		return interestRelationRepository.findAllByRegistrantId(id).stream()
			.map(InterestRelationDto::from)
			.toList();
	}
}
