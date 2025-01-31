package com.gomo.app.interest.application;

import java.util.List;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.common.domain.service.DisplayOrder;
import com.gomo.app.common.domain.service.OrderChangeable;
import com.gomo.app.common.domain.service.OrderChanger;
import com.gomo.app.interest.domain.model.RegistrantId;
import com.gomo.app.interest.domain.repository.MajorInterestRepository;
import com.gomo.app.interest.presentation.request.OrderUpdateMajorInterestRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class OrderUpdateMajorInterestUseCase {

	private final MajorInterestRepository majorInterestRepository;

	public void update(UUID accessorId, OrderUpdateMajorInterestRequest request) {
		List<OrderChangeable> majorInterests = majorInterestRepository.findAllByRegistrantIdOrderByDisplayOrder(RegistrantId.of(accessorId)).stream()
			.map(majorInterest -> (OrderChangeable) majorInterest)
			.toList();
		List<DisplayOrder> changedOrders = request.getUpdatedOrders().stream()
			.map(DisplayOrder::of)
			.toList();

		OrderChanger.change(majorInterests, changedOrders);
	}
}
