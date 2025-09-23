package com.gomo.app.quest.application;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.common.util.DateRangeCalculator;
import com.gomo.app.displayorder.OrderChangeable;
import com.gomo.app.displayorder.OrderChanger;
import com.gomo.app.displayorder.OrderUpdateOrderChangeableCommand;
import com.gomo.app.logging.AuditLog;
import com.gomo.app.quest.application.port.command.OrderUpdateAssignQuestCommand;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.repository.AssignQuestRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class OrderUpdateAssignQuestUseCase {

	private final AssignQuestRepository assignQuestRepository;

	@AuditLog(action = "UPDATE_ASSIGN_QUEST_ORDER")
	public void update(OrderUpdateAssignQuestCommand command) {
		LocalDate now = LocalDate.now();
		QuestType questType = QuestType.valueOf(command.questType());
		Map<UUID, OrderChangeable> assignQuestMap = assignQuestRepository.findParticipatingQuestByQuestTypeWithoutCompleted(
			ParticipantId.of(command.participantId()),
			questType,
			DateRangeCalculator.startOf(now, questType.name()),
			DateRangeCalculator.endOf(now, questType.name())
		).stream().collect(Collectors.toMap(
			assignQuest -> assignQuest.getId().getId(),
			assignQuest -> assignQuest
		));
		OrderChanger.change(OrderUpdateOrderChangeableCommand.of(assignQuestMap, command.updatedOrders()));
	}
}
