package com.gomo.app.core.interest.application.service;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.displayorder.OrderChangeable;
import com.gomo.app.common.displayorder.OrderChanger;
import com.gomo.app.common.displayorder.OrderUpdateOrderChangeableCommand;
import com.gomo.app.common.logging.AuditLog;
import com.gomo.app.core.interest.application.port.command.OrderUpdateMajorInterestCommand;
import com.gomo.app.core.interest.application.port.in.OrderUpdateMajorInterestUseCase;
import com.gomo.app.core.interest.domain.repository.MajorInterestRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
class OrderUpdateMajorInterestService implements OrderUpdateMajorInterestUseCase {

	private final MajorInterestRepository majorInterestRepository;

	@AuditLog(action = "UPDATE_MAJOR_INTEREST_ORDER")
	public void update(OrderUpdateMajorInterestCommand command) {
		Map<UUID, OrderChangeable> majorInterestMap = majorInterestRepository.findAllByRegistrantIdOrderByDisplayOrder(command.registrantId()).stream()
			.collect(Collectors.toMap(
				majorInterest -> majorInterest.getId(),
				majorInterest -> majorInterest
			));

		OrderChanger.change(OrderUpdateOrderChangeableCommand.of(majorInterestMap, command.updatedOrders()));
	}
}
