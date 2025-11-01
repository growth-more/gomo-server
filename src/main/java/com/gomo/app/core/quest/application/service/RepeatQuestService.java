package com.gomo.app.core.quest.application.service;

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
import com.gomo.app.core.quest.application.port.command.OrderUpdateRepeatQuestCommand;
import com.gomo.app.core.quest.application.port.command.UpdateRepeatQuestCommand;
import com.gomo.app.core.quest.application.port.dto.ListRepeatQuestDto;
import com.gomo.app.core.quest.application.port.dto.RepeatQuestDto;
import com.gomo.app.core.quest.application.port.in.RepeatQuestDeleter;
import com.gomo.app.core.quest.application.port.in.RepeatQuestOrderUpdater;
import com.gomo.app.core.quest.application.port.in.RepeatQuestReader;
import com.gomo.app.core.quest.application.port.in.RepeatQuestUpdater;
import com.gomo.app.core.quest.domain.exception.QuestTypeConstraintViolationException;
import com.gomo.app.core.quest.domain.exception.RepeatQuestNotFoundException;
import com.gomo.app.core.quest.domain.exception.code.QuestTypeErrorCode;
import com.gomo.app.core.quest.domain.exception.code.RepeatQuestErrorCode;
import com.gomo.app.core.quest.domain.model.quest.QuestContent;
import com.gomo.app.core.quest.domain.model.quest.QuestType;
import com.gomo.app.core.quest.domain.model.repeat.RepeatQuest;
import com.gomo.app.core.quest.domain.model.reward.QuestReward;
import com.gomo.app.core.quest.domain.model.subject.SubjectName;
import com.gomo.app.core.quest.domain.repository.RepeatQuestRepository;
import com.gomo.app.core.quest.domain.service.QuestRewardProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
class RepeatQuestService implements RepeatQuestReader, RepeatQuestUpdater, RepeatQuestOrderUpdater, RepeatQuestDeleter {

	private final QuestRewardProvider questRewardProvider;
	private final RepeatQuestRepository repeatQuestRepository;

	RepeatQuest readById(UUID repeatQuestId) {
		return repeatQuestRepository.findById(repeatQuestId)
			.orElseThrow(() -> new RepeatQuestNotFoundException(RepeatQuestErrorCode.NOT_FOUND));
	}

	@Override
	@Transactional(readOnly = true)
	public ListRepeatQuestDto readAll(UUID participantId) {
		List<RepeatQuestDto> dailyRepeatQuests = findRepeatQuestResponses(participantId, QuestType.DAILY);
		List<RepeatQuestDto> weeklyRepeatQuests = findRepeatQuestResponses(participantId, QuestType.WEEKLY);
		List<RepeatQuestDto> monthlyRepeatQuests = findRepeatQuestResponses(participantId, QuestType.MONTHLY);
		return ListRepeatQuestDto.of(dailyRepeatQuests, weeklyRepeatQuests, monthlyRepeatQuests);
	}

	private List<RepeatQuestDto> findRepeatQuestResponses(UUID participantId, QuestType questType) {
		return repeatQuestRepository.findRepeatQuestsByQuestType(participantId, questType).stream()
			.map(assignQuest -> {
				QuestReward questReward = questRewardProvider.provide(questType);
				return RepeatQuestDto.from(assignQuest, questReward.pointValue(), questReward.scoreValue());
			}).toList();
	}

	@Override
	@AuditLog(action = "REPEAT_QUEST_UPDATE")
	public void update(UpdateRepeatQuestCommand command) {
		RepeatQuest repeatQuest = readById(command.repeatQuestId());
		repeatQuest.validateAuthority(command.participantId());
		QuestType requestedQuestType = QuestType.valueOf(command.questType());
		ensureSameQuestType(repeatQuest, requestedQuestType);

		repeatQuest.updateQuest(
			command.subjectId(),
			SubjectName.of(command.subjectName()),
			requestedQuestType,
			QuestContent.of(command.content())
		);
	}

	private void ensureSameQuestType(RepeatQuest repeatQuest, QuestType questType) {
		if (!repeatQuest.isSameQuestType(questType)) {
			throw new QuestTypeConstraintViolationException(QuestTypeErrorCode.MISMATCHED);
		}
	}

	@Override
	@AuditLog(action = "REPEAT_QUEST_ORDER_UPDATE")
	public void update(OrderUpdateRepeatQuestCommand command) {
		Map<UUID, OrderChangeable> repeatQuestMap = repeatQuestRepository.findRepeatQuestsByQuestType(
			command.participantId(),
			QuestType.valueOf(command.questType())
		).stream().collect(Collectors.toMap(
			repeatQuest -> repeatQuest.getId(),
			repeatQuest -> repeatQuest
		));
		OrderChanger.change(OrderUpdateOrderChangeableCommand.of(repeatQuestMap, command.updatedOrders()));
	}

	@Override
	@AuditLog(action = "REPEAT_QUEST_DELETE")
	public void delete(UUID participantId, UUID repeatQuestId) {
		RepeatQuest repeatQuest = readById(repeatQuestId);
		repeatQuest.validateAuthority(participantId);
		repeatQuestRepository.delete(repeatQuest);
	}
}
