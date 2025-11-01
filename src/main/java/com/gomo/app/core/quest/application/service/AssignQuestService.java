package com.gomo.app.core.quest.application.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.displayorder.OrderChangeable;
import com.gomo.app.common.displayorder.OrderChanger;
import com.gomo.app.common.displayorder.OrderUpdateOrderChangeableCommand;
import com.gomo.app.common.logging.AuditLog;
import com.gomo.app.common.util.DateRangeCalculator;
import com.gomo.app.core.quest.application.port.command.ListAssignQuestCommand;
import com.gomo.app.core.quest.application.port.command.OrderUpdateAssignQuestCommand;
import com.gomo.app.core.quest.application.port.command.UpdateAssignQuestCommand;
import com.gomo.app.core.quest.application.port.dto.AssignQuestDetailDto;
import com.gomo.app.core.quest.application.port.dto.AssignQuestDto;
import com.gomo.app.core.quest.application.port.dto.ListAssignQuestDetailDto;
import com.gomo.app.core.quest.application.port.in.AssignQuestConfirmer;
import com.gomo.app.core.quest.application.port.in.AssignQuestDeleter;
import com.gomo.app.core.quest.application.port.in.AssignQuestDetailReader;
import com.gomo.app.core.quest.application.port.in.AssignQuestOrderUpdater;
import com.gomo.app.core.quest.application.port.in.AssignQuestReader;
import com.gomo.app.core.quest.application.port.in.AssignQuestUpdater;
import com.gomo.app.core.quest.domain.exception.AssignQuestNotFoundException;
import com.gomo.app.core.quest.domain.exception.code.AssignQuestErrorCode;
import com.gomo.app.core.quest.domain.model.assign.AssignQuest;
import com.gomo.app.core.quest.domain.model.quest.QuestContent;
import com.gomo.app.core.quest.domain.model.quest.QuestType;
import com.gomo.app.core.quest.domain.model.reward.QuestReward;
import com.gomo.app.core.quest.domain.model.subject.SubjectName;
import com.gomo.app.core.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.core.quest.domain.service.QuestRewardProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
class AssignQuestService
	implements AssignQuestReader, AssignQuestDetailReader, AssignQuestUpdater, AssignQuestOrderUpdater, AssignQuestDeleter, AssignQuestConfirmer {

	private final QuestRewardProvider questRewardProvider;
	private final AssignQuestRepository assignQuestRepository;

	@Override
	@Transactional(readOnly = true)
	public List<AssignQuestDto> readAll(ListAssignQuestCommand command) {
		UUID targetId = command.participantId();
		LocalDateTime startDate = command.startDate();
		LocalDateTime endDate = command.endDate();
		List<AssignQuestDto> calendars;
		if (command.isCompleted()) {
			calendars = assignQuestRepository.findByQuestParticipantIdAndCompletedDateTimeBetween(targetId, startDate, endDate)
				.stream()
				.map(AssignQuestDto::of)
				.toList();
		} else {
			calendars = assignQuestRepository.findByQuestParticipantIdAndStartDateTimeBetweenAndIsCompletedFalse(targetId, startDate, endDate)
				.stream()
				.map(AssignQuestDto::of)
				.toList();
		}
		return calendars;
	}

	@Override
	@Transactional(readOnly = true)
	public ListAssignQuestDetailDto readAll(UUID participantId) {
		List<AssignQuestDetailDto> dailyQuests = readAllByQuestType(participantId, QuestType.DAILY);
		List<AssignQuestDetailDto> weeklyQuests = readAllByQuestType(participantId, QuestType.WEEKLY);
		List<AssignQuestDetailDto> monthlyQuests = readAllByQuestType(participantId, QuestType.MONTHLY);
		return ListAssignQuestDetailDto.of(dailyQuests, weeklyQuests, monthlyQuests);
	}

	private List<AssignQuestDetailDto> readAllByQuestType(UUID participantId, QuestType questType) {
		return assignQuestRepository.findParticipatingQuestByQuestType(
			participantId,
			questType,
			DateRangeCalculator.startOf(LocalDate.now(), questType.name()),
			DateRangeCalculator.endOf(LocalDate.now(), questType.name())
		).stream().map(assignQuest -> {
			QuestReward questReward = questRewardProvider.provide(questType);
			return AssignQuestDetailDto.from(assignQuest, questReward.pointValue(), questReward.scoreValue());
		}).toList();
	}

	AssignQuest readById(UUID assignQuestId) {
		return assignQuestRepository.findById(assignQuestId)
			.orElseThrow(() -> new AssignQuestNotFoundException(AssignQuestErrorCode.NOT_FOUND));
	}

	@Override
	@AuditLog(action = "ASSIGN_QUEST_UPDATE")
	public void update(UpdateAssignQuestCommand command) {
		AssignQuest assignQuest = readById(command.assignQuestId());
		assignQuest.validateAuthority(command.participantId());

		QuestType requestedQuestType = QuestType.valueOf(command.questType());
		assignQuest.ensureSameQuestType(requestedQuestType);
		assignQuest.ensureNotConfirmed();
		assignQuest.ensureNotCompleted();

		assignQuest.updateQuest(
			command.subjectId(),
			SubjectName.of(command.subjectName()),
			requestedQuestType,
			QuestContent.of(command.content())
		);
	}

	@Override
	@AuditLog(action = "ASSIGN_QUEST_ORDER_UPDATE")
	public void update(OrderUpdateAssignQuestCommand command) {
		LocalDate now = LocalDate.now();
		QuestType questType = QuestType.valueOf(command.questType());
		Map<UUID, OrderChangeable> assignQuestMap = assignQuestRepository.findParticipatingQuestByQuestTypeWithoutCompleted(
			command.participantId(),
			questType,
			DateRangeCalculator.startOf(now, questType.name()),
			DateRangeCalculator.endOf(now, questType.name())
		).stream().collect(Collectors.toMap(
			assignQuest -> assignQuest.getId(),
			assignQuest -> assignQuest
		));
		OrderChanger.change(OrderUpdateOrderChangeableCommand.of(assignQuestMap, command.updatedOrders()));
	}

	@Override
	@AuditLog(action = "ASSIGN_QUEST_CONFIRM")
	public void confirm(UUID accessorId, UUID assignQuestId) {
		AssignQuest assignQuest = readById(assignQuestId);
		assignQuest.validateAuthority(accessorId);
		assignQuest.confirm();
	}

	@Override
	@AuditLog(action = "ASSIGN_QUEST_DELETE")
	public void delete(UUID participantId, UUID assignQuestId) {
		AssignQuest assignQuest = readById(assignQuestId);
		assignQuest.validateAuthority(participantId);
		assignQuest.ensureNotConfirmed();
		assignQuest.ensureNotCompleted();
		assignQuestRepository.delete(assignQuest);
	}
}
