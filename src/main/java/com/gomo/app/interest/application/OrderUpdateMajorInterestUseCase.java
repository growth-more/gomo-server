package com.gomo.app.interest.application;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.displayorder.OrderChangeable;
import com.gomo.app.displayorder.OrderChanger;
import com.gomo.app.displayorder.OrderUpdateOrderChangeableCommand;
import com.gomo.app.interest.application.port.command.OrderUpdateMajorInterestCommand;
import com.gomo.app.interest.domain.model.RegistrantId;
import com.gomo.app.interest.domain.repository.MajorInterestRepository;
import com.gomo.app.logging.AuditLog;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class OrderUpdateMajorInterestUseCase {

	private final MajorInterestRepository majorInterestRepository;

	@AuditLog(action = "UPDATE_MAJOR_INTEREST_ORDER")
	public void update(OrderUpdateMajorInterestCommand command) {
		Map<UUID, OrderChangeable> majorInterestMap = majorInterestRepository.findAllByRegistrantIdOrderByDisplayOrder(RegistrantId.of(command.registrantId())).stream()
			.collect(Collectors.toMap(
				majorInterest -> majorInterest.uuid(),
				majorInterest -> majorInterest
			));

		OrderChanger.change(OrderUpdateOrderChangeableCommand.of(majorInterestMap, command.updatedOrders()));
	}
}
