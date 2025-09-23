package com.gomo.app.quest.application;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.displayorder.OrderChangeable;
import com.gomo.app.displayorder.OrderChanger;
import com.gomo.app.displayorder.OrderUpdateOrderChangeableCommand;
import com.gomo.app.logging.AuditLog;
import com.gomo.app.quest.application.port.command.OrderUpdateRepeatQuestCommand;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.repository.RepeatQuestRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class OrderUpdateRepeatQuestUseCase {

	private final RepeatQuestRepository repeatQuestRepository;

	@AuditLog(action = "UPDATE_REPEAT_QUEST_ORDER")
	public void update(OrderUpdateRepeatQuestCommand command) {
		Map<UUID, OrderChangeable> repeatQuestMap = repeatQuestRepository.findRepeatQuestsByQuestType(
				ParticipantId.of(command.participantId()),
				command.questType()
			).stream()
			.collect(Collectors.toMap(
				repeatQuest -> repeatQuest.getId().getId(),
				repeatQuest -> repeatQuest
			));

		OrderChanger.change(OrderUpdateOrderChangeableCommand.of(repeatQuestMap, command.updatedOrders()));
	}
}
