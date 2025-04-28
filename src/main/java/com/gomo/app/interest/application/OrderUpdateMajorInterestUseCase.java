package com.gomo.app.interest.application;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.displayorder.OrderChangeable;
import com.gomo.app.displayorder.OrderChanger;
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
		Map<UUID, OrderChangeable> majorInterestMap = majorInterestRepository.findAllByRegistrantIdOrderByDisplayOrder(RegistrantId.of(accessorId)).stream()
			.collect(Collectors.toMap(
				majorInterest -> majorInterest.uuid(),
				majorInterest -> majorInterest
			));

		OrderChanger.change(majorInterestMap, request.getUpdateOrderRequests());
	}
}
