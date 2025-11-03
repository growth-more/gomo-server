package com.gomo.app.core.interest.application.service;

import java.util.List;
import java.util.UUID;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.core.interest.application.port.dto.InterestDto;
import com.gomo.app.core.interest.application.port.dto.InterestNetworkDto;
import com.gomo.app.core.interest.application.port.dto.InterestRelationDto;
import com.gomo.app.core.interest.application.port.in.InterestNetworkReader;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
class InterestNetworkService implements InterestNetworkReader {

	private final InterestService interestService;
	private final InterestRelationService interestRelationService;

	public InterestNetworkDto read(UUID registrantId) {
		List<InterestDto> interestDtos = interestService.readAll(registrantId);
		List<InterestRelationDto> relationDtos = interestRelationService.readAllByRegistrantId(registrantId).stream()
			.map(InterestRelationDto::from)
			.toList();
		return InterestNetworkDto.of(interestDtos, relationDtos);
	}
}
