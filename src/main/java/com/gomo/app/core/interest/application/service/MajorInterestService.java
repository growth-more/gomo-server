package com.gomo.app.core.interest.application.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.displayorder.OrderChangeable;
import com.gomo.app.common.displayorder.OrderChanger;
import com.gomo.app.common.displayorder.OrderUpdateOrderChangeableCommand;
import com.gomo.app.common.logging.AuditLog;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.core.interest.application.port.command.OrderUpdateMajorInterestCommand;
import com.gomo.app.core.interest.application.port.dto.MajorInterestDto;
import com.gomo.app.core.interest.application.port.in.MajorInterestCreator;
import com.gomo.app.core.interest.application.port.in.MajorInterestDeleter;
import com.gomo.app.core.interest.application.port.in.MajorInterestOrderUpdater;
import com.gomo.app.core.interest.application.port.in.MajorInterestReader;
import com.gomo.app.core.interest.domain.exception.MajorInterestDuplicatedException;
import com.gomo.app.core.interest.domain.exception.MajorInterestNotFoundException;
import com.gomo.app.core.interest.domain.exception.code.MajorInterestErrorCode;
import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.model.MajorInterest;
import com.gomo.app.core.interest.domain.repository.InterestRepository;
import com.gomo.app.core.interest.domain.repository.MajorInterestRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
class MajorInterestService implements MajorInterestCreator, MajorInterestReader, MajorInterestOrderUpdater, MajorInterestDeleter {

	private final InterestService interestService;
	private final MajorInterestRepository majorInterestRepository;
	private final InterestRepository interestRepository;

	@Override
	@AuditLog(action = "CREATE_MAJOR_INTEREST")
	public UUID create(UUID registrantId, UUID interestId) {
		Interest interest = interestService.readById(interestId);
		interest.validateAuthority(registrantId);
		ensureNotDuplicated(interest);
		int highestOrder = majorInterestRepository.findMaxDisplayOrder(interest.getRegistrantId());
		MajorInterest next = MajorInterest.createNext(UUIDGenerator.generate(), interest.getRegistrantId(), interest.getId(), highestOrder);
		MajorInterest saved = majorInterestRepository.save(next);
		return saved.getId();
	}

	private void ensureNotDuplicated(Interest interest) {
		majorInterestRepository.findByInterestId(interest.getId())
			.ifPresent(exists -> {
				throw new MajorInterestDuplicatedException(MajorInterestErrorCode.DUPLICATED);
			});
	}

	@Override
	@Transactional(readOnly = true)
	public List<MajorInterestDto> readAll(UUID registrantId) {
		List<MajorInterest> majorInterests = majorInterestRepository.findAllByRegistrantIdOrderByDisplayOrder(registrantId);
		Map<UUID, MajorInterest> majorInterestByInterestId = majorInterests.stream()
			.collect((Collectors.toMap(MajorInterest::getInterestId, Function.identity())));

		List<Interest> interests = interestRepository.findAllById(majorInterestByInterestId.keySet());
		return interests.stream()
			.map(interest -> MajorInterestDto.from(majorInterestByInterestId.get(interest.getId()), interest))
			.toList();
	}

	MajorInterest readById(UUID majorInterestId) {
		return majorInterestRepository.findById(majorInterestId)
			.orElseThrow(() -> new MajorInterestNotFoundException(MajorInterestErrorCode.NOT_FOUND));
	}

	@AuditLog(action = "UPDATE_MAJOR_INTEREST_ORDER")
	public void update(OrderUpdateMajorInterestCommand command) {
		Map<UUID, OrderChangeable> majorInterestMap = majorInterestRepository.findAllByRegistrantIdOrderByDisplayOrder(command.registrantId()).stream()
			.collect(Collectors.toMap(
				majorInterest -> majorInterest.getId(),
				majorInterest -> majorInterest
			));
		OrderChanger.change(OrderUpdateOrderChangeableCommand.of(majorInterestMap, command.updatedOrders()));
	}

	@AuditLog(action = "DELETE_MAJOR_INTEREST")
	public void delete(UUID registrantId, UUID majorInterestId) {
		MajorInterest majorInterest = readById(majorInterestId);
		majorInterest.validateAuthority(registrantId);
		majorInterestRepository.delete(majorInterest);
	}
}
