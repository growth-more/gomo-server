package com.gomo.app.interest.application;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.MajorInterest;
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
		List<ReadInterestResponse> interestResponses = getReadInterestResponses(registrantId);
		List<ReadInterestRelationResponse> relationResponses = interestRelationRepository.findAllByRegistrantId(registrantId).stream()
			.map(ReadInterestRelationResponse::of)
			.toList();
		return InterestNetworkResponse.of(interestResponses, relationResponses);
	}

	@NotNull
	private List<ReadInterestResponse> getReadInterestResponses(RegistrantId registrantId) {
		List<Interest> interests = interestRepository.findAllByRegistrantId(registrantId);
		List<InterestId> interestIds = interests.stream().map(Interest::getId).toList();
		Map<UUID, UUID> majorInterestMap = majorInterestRepository.findAllByRegistrantIdAndInterestIdIn(registrantId, interestIds).stream()
			.collect(Collectors.toMap(MajorInterest::interestUuid, MajorInterest::uuid));

		return interests.stream().map(interest -> {
			UUID majorInterestId = majorInterestMap.get(interest.uuid());
			return ReadInterestResponse.of(interest, majorInterestId);
		}).toList();
	}
}
