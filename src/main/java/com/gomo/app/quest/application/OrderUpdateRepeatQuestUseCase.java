package com.gomo.app.quest.application;

import java.util.List;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.common.domain.service.DisplayOrder;
import com.gomo.app.common.domain.service.OrderChangeable;
import com.gomo.app.common.domain.service.OrderChanger;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.repository.RepeatQuestRepository;
import com.gomo.app.quest.presentation.request.OrderUpdateRepeatQuestRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class OrderUpdateRepeatQuestUseCase {

	private final RepeatQuestRepository repeatQuestRepository;

	public void update(UUID accessorId, OrderUpdateRepeatQuestRequest request) {
		List<OrderChangeable> repeatQuests = repeatQuestRepository.findRepeatQuestsByQuestType(
				ParticipantId.of(accessorId),
				request.getQuestType()
			)
			.stream()
			.map(repeatQuest -> (OrderChangeable)repeatQuest)
			.toList();

		List<DisplayOrder> changedOrders = request.getUpdatedOrders().stream()
			.map(DisplayOrder::of)
			.toList();

		OrderChanger.change(repeatQuests, changedOrders);
	}
}
