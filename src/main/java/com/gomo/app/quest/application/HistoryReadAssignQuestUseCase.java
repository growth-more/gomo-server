package com.gomo.app.quest.application;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.common.dto.PageRequest;
import com.gomo.app.common.util.DateRangeCalculator;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.quest.presentation.response.HistoryListAssignQuestResponse;
import com.gomo.app.quest.presentation.response.HistoryReadAssignQuestResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class HistoryReadAssignQuestUseCase {

	private final AssignQuestRepository assignQuestRepository;

	public HistoryListAssignQuestResponse findAll(ParticipantId participantId, PageRequest request) {
		LocalDate now = LocalDate.now();

		return HistoryListAssignQuestResponse.of(
			findHistoryQuests(QuestType.DAILY, participantId, request, now),
			findHistoryQuests(QuestType.WEEKLY, participantId, request, now),
			findHistoryQuests(QuestType.MONTHLY, participantId, request, now)
		);
	}

	@NotNull
	private List<HistoryReadAssignQuestResponse> findHistoryQuests(QuestType questType, ParticipantId participantId, PageRequest request, LocalDate now) {
		return assignQuestRepository.findHistoryQuestByQuestType(
				participantId.toString(),
				questType.name(),
				DateRangeCalculator.startOf(now, questType.name()),
				DateRangeCalculator.endOf(now, questType.name()),
				Optional.ofNullable(request.getLastElementId()).map(UUID::toString).orElse(null),
				request.getSize())
			.stream()
			.map(HistoryReadAssignQuestResponse::of)
			.toList();
	}
}
