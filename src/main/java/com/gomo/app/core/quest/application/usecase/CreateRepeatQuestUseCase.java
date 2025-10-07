package com.gomo.app.core.quest.application.usecase;

import java.util.UUID;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.core.quest.application.port.ReadParticipantPortOut;
import com.gomo.app.core.quest.application.port.command.CreateRepeatQuestCommand;
import com.gomo.app.core.quest.application.port.dto.ParticipantDto;
import com.gomo.app.core.quest.domain.model.participant.Participant;
import com.gomo.app.core.quest.domain.model.participant.ParticipantId;
import com.gomo.app.core.quest.domain.model.participant.QuestQuota;
import com.gomo.app.core.quest.domain.model.quest.Quest;
import com.gomo.app.core.quest.domain.model.quest.QuestContent;
import com.gomo.app.core.quest.domain.model.quest.QuestType;
import com.gomo.app.core.quest.domain.model.repeat.RepeatQuest;
import com.gomo.app.core.quest.domain.model.subject.SubjectId;
import com.gomo.app.core.quest.domain.model.subject.SubjectName;
import com.gomo.app.core.quest.domain.repository.RepeatQuestRepository;
import com.gomo.app.core.quest.domain.service.RepeatQuestService;
import com.gomo.app.support.logging.AuditLog;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class CreateRepeatQuestUseCase {

	private final ReadParticipantPortOut readParticipantPortOut;
	private final RepeatQuestService repeatQuestService;
	private final RepeatQuestRepository repeatQuestRepository;

	@AuditLog(action = "CREATE_REPEAT_QUEST")
	public UUID create(CreateRepeatQuestCommand command) {
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
		RepeatQuest savedRepeatQuest = repeatQuestService.create(ParticipantId.of(participantId), quest);
		return savedRepeatQuest.id();
	}

	private void ensureNotExceedQuestQuota(UUID participantId, QuestType questType) {
		ParticipantDto dto = readParticipantPortOut.find(participantId);
		Participant participant = Participant.of(
			ParticipantId.of(dto.id()),
			QuestQuota.of(dto.dailyQuota(), dto.weeklyQuota(), dto.monthlyQuota())
		);
		int repeatQuestCount = (int)repeatQuestRepository.countByQuestParticipantIdAndQuestType(participant.getId(), questType);
		participant.validateQuestQuota(questType, repeatQuestCount);
	}
}
