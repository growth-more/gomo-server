package com.gomo.app.core.quest.application.usecase;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.logging.AuditLog;
import com.gomo.app.common.util.DateRangeCalculator;
import com.gomo.app.core.quest.application.port.ReadParticipantPortOut;
import com.gomo.app.core.quest.application.port.command.CreateAssignQuestCommand;
import com.gomo.app.core.quest.application.port.dto.ParticipantDto;
import com.gomo.app.core.quest.domain.model.assign.AssignQuest;
import com.gomo.app.core.quest.domain.model.participant.Participant;
import com.gomo.app.core.quest.domain.model.participant.QuestQuota;
import com.gomo.app.core.quest.domain.model.quest.Quest;
import com.gomo.app.core.quest.domain.model.quest.QuestContent;
import com.gomo.app.core.quest.domain.model.quest.QuestType;
import com.gomo.app.core.quest.domain.model.subject.SubjectName;
import com.gomo.app.core.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.core.quest.domain.service.AssignQuestService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
public class CreateAssignQuestUseCase {

	private final ReadParticipantPortOut readParticipantPortOut;
	private final AssignQuestService assignQuestService;
	private final AssignQuestRepository assignQuestRepository;

	@AuditLog(action = "CREATE_ASSIGN_QUEST")
	public UUID create(CreateAssignQuestCommand command) {
		UUID participantId = command.participantId();
		QuestType questType = QuestType.valueOf(command.questType());
		ensureNotExceedQuestQuota(participantId, questType);
		Quest quest = Quest.of(
			participantId,
			command.subjectId(),
			SubjectName.of(command.subjectName()),
			questType,
			QuestContent.of(command.content())
		);
		AssignQuest savedAssignQuest = assignQuestService.create(participantId, quest);
		return savedAssignQuest.getId();
	}

	private void ensureNotExceedQuestQuota(UUID participantId, QuestType questType) {
		ParticipantDto dto = readParticipantPortOut.find(participantId);
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
}
