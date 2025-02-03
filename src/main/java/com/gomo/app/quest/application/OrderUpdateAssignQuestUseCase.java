package com.gomo.app.quest.application;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.common.domain.service.DisplayOrder;
import com.gomo.app.common.domain.service.OrderChangeable;
import com.gomo.app.common.domain.service.OrderChanger;
import com.gomo.app.common.util.DateRangeCalculator;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.quest.presentation.request.OrderUpdateAssignQuestRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class OrderUpdateAssignQuestUseCase {

	private final AssignQuestRepository assignQuestRepository;

	public void update(UUID accessorId, OrderUpdateAssignQuestRequest request) {
		LocalDate now = LocalDate.now();

		List<OrderChangeable> assignQuests = assignQuestRepository.findParticipatingQuestByQuestType(
				ParticipantId.of(accessorId),
				request.getQuestType(),
				DateRangeCalculator.startOf(now, request.getQuestType().name()),
				DateRangeCalculator.endOf(now, request.getQuestType().name())
			)
			.stream()
			.map(assignQuest -> (OrderChangeable)assignQuest)
			.toList();

		List<DisplayOrder> changedOrders = request.getUpdatedOrders().stream()
			.map(DisplayOrder::of)
			.toList();

		OrderChanger.change(assignQuests, changedOrders);
	}
}
