package com.gomo.app.core.quest.application;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.common.displayorder.OrderChangeable;
import com.gomo.app.common.displayorder.OrderChanger;
import com.gomo.app.common.displayorder.OrderUpdateOrderChangeableCommand;
import com.gomo.app.support.logging.AuditLog;
import com.gomo.app.core.quest.application.port.command.OrderUpdateRepeatQuestCommand;
import com.gomo.app.core.quest.domain.model.ParticipantId;
import com.gomo.app.core.quest.domain.model.QuestType;
import com.gomo.app.core.quest.domain.repository.RepeatQuestRepository;

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
			QuestType.valueOf(command.questType())
		).stream().collect(Collectors.toMap(
			repeatQuest -> repeatQuest.getId().getId(),
			repeatQuest -> repeatQuest
		));
		OrderChanger.change(OrderUpdateOrderChangeableCommand.of(repeatQuestMap, command.updatedOrders()));
	}
}
