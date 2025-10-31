package com.gomo.app.core.interest.application.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.logging.AuditLog;
import com.gomo.app.core.interest.application.port.command.UpdateInterestCommand;
import com.gomo.app.core.interest.application.port.dto.InterestDto;
import com.gomo.app.core.interest.application.port.in.InterestReader;
import com.gomo.app.core.interest.application.port.in.InterestUpdater;
import com.gomo.app.core.interest.domain.exception.InterestNotFoundException;
import com.gomo.app.core.interest.domain.exception.code.InterestErrorCode;
import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.model.InterestName;
import com.gomo.app.core.interest.domain.model.MajorInterest;
import com.gomo.app.core.interest.domain.repository.InterestRepository;
import com.gomo.app.core.interest.domain.repository.MajorInterestRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
class InterestService implements InterestReader, InterestUpdater {

	private final InterestRepository interestRepository;
	private final MajorInterestRepository majorInterestRepository;

	// TODO [2025-10-18] jhl221123 : InterestDto 에서 majorInterestId를 제거하고, InterestDetailDto를 추가해야합니다.
	@Override
	@Transactional(readOnly = true)
	public InterestDto read(UUID interestId) {
		Interest interest = readById(interestId);
		UUID majorInterestId = majorInterestRepository.findByInterestId(interestId)
			.map(MajorInterest::getId)
			.orElse(null);
		return InterestDto.of(interest, majorInterestId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<InterestDto> readAll(UUID registrantId) {
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
	@Transactional(readOnly = true)
	public List<InterestDto> readAllByRegistrantIds(Set<UUID> registrantIds) {
		List<Interest> interests = interestRepository.findAllByRegistrantIdIn(registrantIds);
		List<UUID> interestIds = interests.stream().map(Interest::getId).toList();
		Map<UUID, UUID> majorIdsByInterestId = majorInterestRepository.findByInterestIdIn(interestIds).stream()
			.collect(Collectors.toMap(MajorInterest::getInterestId, MajorInterest::getId));

		return interests.stream().map(interest -> {
			UUID majorInterestId = majorIdsByInterestId.get(interest.getId());
			return InterestDto.of(interest, majorInterestId);
		}).toList();
	}

	Interest readById(UUID interestId) {
		return interestRepository.findById(interestId)
			.orElseThrow(() -> new InterestNotFoundException(InterestErrorCode.NOT_FOUND));
	}

	@AuditLog(action = "UPDATE_INTEREST")
	public void update(UpdateInterestCommand command) {
		Interest interest = readById(command.interestId());
		interest.validateAuthority(command.registrantId());
		interest.updateName(InterestName.of(command.name()));
		interest.updateColorCode(command.colorCode());
	}
}
