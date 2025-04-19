package com.gomo.app.quest.application;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.application.ApplicationService;
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
		Map<UUID, OrderChangeable> repeatQuestMap = repeatQuestRepository.findRepeatQuestsByQuestType(
				ParticipantId.of(accessorId),
				request.getQuestType()
			).stream()
			.collect(Collectors.toMap(
				repeatQuest -> repeatQuest.getId().getId(),
				repeatQuest -> repeatQuest
			));

		OrderChanger.change(repeatQuestMap, request.getUpdateOrderRequests());
	}
}
