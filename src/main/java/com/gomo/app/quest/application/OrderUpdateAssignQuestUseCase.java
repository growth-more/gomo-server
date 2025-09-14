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
import com.gomo.app.logging.AuditLog;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.quest.presentation.request.OrderUpdateAssignQuestRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class OrderUpdateAssignQuestUseCase {

	private final AssignQuestRepository assignQuestRepository;

	@AuditLog(action = "UPDATE_ASSIGN_QUEST_ORDER")
	public void update(UUID accessorId, OrderUpdateAssignQuestRequest request) {
		LocalDate now = LocalDate.now();

		Map<UUID, OrderChangeable> assignQuestMap = assignQuestRepository.findParticipatingQuestByQuestTypeWithoutCompleted(
				ParticipantId.of(accessorId),
				request.getQuestType(),
				DateRangeCalculator.startOf(now, request.getQuestType().name()),
				DateRangeCalculator.endOf(now, request.getQuestType().name())
			).stream()
			.collect(Collectors.toMap(
				assignQuest -> assignQuest.getId().getId(),
				assignQuest -> assignQuest
			));

		OrderChanger.change(assignQuestMap, request.getUpdateOrderRequests());
	}
}
