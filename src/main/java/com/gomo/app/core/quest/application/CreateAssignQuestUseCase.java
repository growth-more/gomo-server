package com.gomo.app.core.quest.application;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.common.util.DateRangeCalculator;
import com.gomo.app.support.logging.AuditLog;
import com.gomo.app.core.quest.application.port.ReadParticipantPortOut;
import com.gomo.app.core.quest.application.port.command.CreateAssignQuestCommand;
import com.gomo.app.core.quest.application.port.dto.CreateAssignQuestDto;
import com.gomo.app.core.quest.application.port.dto.ParticipantDto;
import com.gomo.app.core.quest.domain.model.AssignQuest;
import com.gomo.app.core.quest.domain.model.Participant;
import com.gomo.app.core.quest.domain.model.ParticipantId;
import com.gomo.app.core.quest.domain.model.Quest;
import com.gomo.app.core.quest.domain.model.QuestContent;
import com.gomo.app.core.quest.domain.model.QuestQuota;
import com.gomo.app.core.quest.domain.model.QuestType;
import com.gomo.app.core.quest.domain.model.SubjectId;
import com.gomo.app.core.quest.domain.model.SubjectName;
import com.gomo.app.core.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.core.quest.domain.service.AssignQuestService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class CreateAssignQuestUseCase {

	private final ReadParticipantPortOut readParticipantPortOut;
	private final AssignQuestService assignQuestService;
	private final AssignQuestRepository assignQuestRepository;

	@AuditLog(action = "CREATE_ASSIGN_QUEST")
	public CreateAssignQuestDto create(CreateAssignQuestCommand command) {
		UUID participantId = command.participantId();
		QuestType questType = QuestType.valueOf(command.questType());
		ensureNotExceedQuestQuota(participantId, questType);
		Quest quest = Quest.of(
			ParticipantId.of(participantId),
			SubjectId.of(command.subjectId()),
			SubjectName.of(command.subjectName()),
			questType,
			QuestContent.of(command.content())
		);
		AssignQuest savedAssignQuest = assignQuestService.create(ParticipantId.of(participantId), quest);
		return CreateAssignQuestDto.of(savedAssignQuest.id());
	}

	private void ensureNotExceedQuestQuota(UUID participantId, QuestType questType) {
		ParticipantDto dto = readParticipantPortOut.find(participantId);
		Participant participant = Participant.of(
			ParticipantId.of(dto.id()),
			QuestQuota.of(dto.dailyQuota(), dto.weeklyQuota(), dto.monthlyQuota())
		);
		int participatingQuestCount = countParticipatingQuest(participant.getId(), questType);
		participant.validateQuestQuota(questType, participatingQuestCount);
	}

	private int countParticipatingQuest(ParticipantId participantId, QuestType questType) {
		LocalDate now = LocalDate.now();
		LocalDateTime startDateTime = DateRangeCalculator.startOf(now, questType.name());
		LocalDateTime endDateTime = DateRangeCalculator.endOf(now, questType.name());
		return (int)assignQuestRepository.countParticipatingQuestByQuestType(participantId, questType, startDateTime, endDateTime);
	}
}
