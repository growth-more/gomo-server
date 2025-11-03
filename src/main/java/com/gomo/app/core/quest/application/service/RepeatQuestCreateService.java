package com.gomo.app.core.quest.application.service;

import java.util.UUID;

import org.jetbrains.annotations.NotNull;
import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.displayorder.DisplayOrder;
import com.gomo.app.common.logging.AuditLog;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.core.quest.application.port.command.CreateRepeatQuestCommand;
import com.gomo.app.core.quest.application.port.dto.ParticipantDto;
import com.gomo.app.core.quest.application.port.in.RepeatQuestCreator;
import com.gomo.app.core.quest.application.port.out.ParticipantReader;
import com.gomo.app.core.quest.domain.model.participant.Participant;
import com.gomo.app.core.quest.domain.model.participant.QuestQuota;
import com.gomo.app.core.quest.domain.model.quest.Quest;
import com.gomo.app.core.quest.domain.model.quest.QuestContent;
import com.gomo.app.core.quest.domain.model.quest.QuestType;
import com.gomo.app.core.quest.domain.model.repeat.RepeatQuest;
import com.gomo.app.core.quest.domain.model.subject.SubjectName;
import com.gomo.app.core.quest.domain.repository.RepeatQuestRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
class RepeatQuestCreateService implements RepeatQuestCreator {

	private final ParticipantReader participantReader;
	private final RepeatQuestRepository repeatQuestRepository;

	@AuditLog(action = "REPEAT_QUEST_CREATE")
	public UUID create(CreateRepeatQuestCommand command) {
		UUID participantId = command.participantId();
		QuestType questType = QuestType.valueOf(command.questType());
		ensureNotExceedQuestQuota(participantId, questType);
		RepeatQuest savedRepeatQuest = repeatQuestRepository.save(createRepeatQuest(command, participantId, questType));
		return savedRepeatQuest.getId();
	}

	private void ensureNotExceedQuestQuota(UUID participantId, QuestType questType) {
		ParticipantDto dto = participantReader.read(participantId);
		Participant participant = Participant.of(
			dto.id(),
			QuestQuota.of(dto.dailyQuota(), dto.weeklyQuota(), dto.monthlyQuota())
		);
		int repeatQuestCount = (int)repeatQuestRepository.countByQuestParticipantIdAndQuestType(participant.getId(), questType);
		participant.validateQuestQuota(questType, repeatQuestCount);
	}

	@NotNull
	private RepeatQuest createRepeatQuest(CreateRepeatQuestCommand command, UUID participantId, QuestType questType) {
		Quest quest = Quest.of(
			participantId,
			command.subjectId(),
			SubjectName.of(command.subjectName()),
			questType,
			QuestContent.of(command.content())
		);
		int nextDisplayOrder = repeatQuestRepository.findMaxDisplayOrderByQuestType(participantId, quest.getType()) + 1;
		return RepeatQuest.of(UUIDGenerator.generate(), quest, DisplayOrder.of(nextDisplayOrder));
	}
}
