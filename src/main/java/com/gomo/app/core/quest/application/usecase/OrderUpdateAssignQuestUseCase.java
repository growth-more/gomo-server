package com.gomo.app.core.quest.application.usecase;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.util.DateRangeCalculator;
import com.gomo.app.common.displayorder.OrderChangeable;
import com.gomo.app.common.displayorder.OrderChanger;
import com.gomo.app.common.displayorder.OrderUpdateOrderChangeableCommand;
import com.gomo.app.support.logging.AuditLog;
import com.gomo.app.core.quest.application.port.command.OrderUpdateAssignQuestCommand;
import com.gomo.app.core.quest.domain.model.participant.ParticipantId;
import com.gomo.app.core.quest.domain.model.quest.QuestType;
import com.gomo.app.core.quest.domain.repository.AssignQuestRepository;

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
