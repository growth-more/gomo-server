package com.gomo.app.core.quest.application.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;
import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.displayorder.DisplayOrder;
import com.gomo.app.common.logging.AuditLog;
import com.gomo.app.common.util.DateRangeCalculator;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.core.quest.application.port.command.CreateAssignQuestCommand;
import com.gomo.app.core.quest.application.port.dto.ParticipantDto;
import com.gomo.app.core.quest.application.port.in.AssignQuestCreator;
import com.gomo.app.core.quest.application.port.out.ParticipantReader;
import com.gomo.app.core.quest.domain.model.assign.AssignQuest;
import com.gomo.app.core.quest.domain.model.participant.Participant;
import com.gomo.app.core.quest.domain.model.participant.QuestQuota;
import com.gomo.app.core.quest.domain.model.quest.Quest;
import com.gomo.app.core.quest.domain.model.quest.QuestContent;
import com.gomo.app.core.quest.domain.model.quest.QuestType;
import com.gomo.app.core.quest.domain.model.subject.SubjectName;
import com.gomo.app.core.quest.domain.repository.AssignQuestRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
class AssignQuestCreateService implements AssignQuestCreator {

	private final ParticipantReader participantReader;
	private final AssignQuestRepository assignQuestRepository;

	@AuditLog(action = "ASSIGN_QUEST_CREATE")
	public UUID create(CreateAssignQuestCommand command) {
		UUID participantId = command.participantId();
		QuestType questType = QuestType.valueOf(command.questType());
		ensureNotExceedQuestQuota(participantId, questType);
		AssignQuest assignQuest = assignQuestRepository.save(createAssignQuest(command, participantId, questType));
		return assignQuest.getId();
	}

	private void ensureNotExceedQuestQuota(UUID participantId, QuestType questType) {
		ParticipantDto dto = participantReader.read(participantId);
		Participant participant = Participant.of(
			dto.id(),
			QuestQuota.of(dto.dailyQuota(), dto.weeklyQuota(), dto.monthlyQuota())
		);
		int participatingQuestCount = countParticipatingQuest(participant.getId(), questType);
		participant.validateQuestQuota(questType, participatingQuestCount);
	}

	private int countParticipatingQuest(UUID participantId, QuestType questType) {
		LocalDate now = LocalDate.now();
		LocalDateTime startDateTime = DateRangeCalculator.startOf(now, questType.name());
		LocalDateTime endDateTime = DateRangeCalculator.endOf(now, questType.name());
		return (int)assignQuestRepository.countParticipatingQuestByQuestType(participantId, questType, startDateTime, endDateTime);
	}

	@NotNull
	private AssignQuest createAssignQuest(CreateAssignQuestCommand command, UUID participantId, QuestType questType) {
		Quest quest = Quest.of(
			participantId,
			command.subjectId(),
			SubjectName.of(command.subjectName()),
			questType,
			QuestContent.of(command.content())
		);

		int displayOrder = findMaxDisplayOrderOfParticipatingQuest(participantId, quest.getType()) + 1;
		return AssignQuest.of(
			UUIDGenerator.generate(),
			quest,
			false,
			DisplayOrder.of(displayOrder),
			LocalDateTime.now()
		);
	}

	private int findMaxDisplayOrderOfParticipatingQuest(UUID participantId, QuestType questType) {
		LocalDate now = LocalDate.now();
		LocalDateTime startDateTime = DateRangeCalculator.startOf(now, questType.name());
		LocalDateTime endDateTime = DateRangeCalculator.endOf(now, questType.name());

		return assignQuestRepository.findMaxDisplayOrderOfParticipatingQuest(
			participantId,
			questType,
			startDateTime,
			endDateTime
		);
	}
}
