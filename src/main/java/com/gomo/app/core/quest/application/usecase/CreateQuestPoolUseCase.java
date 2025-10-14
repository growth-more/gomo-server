package com.gomo.app.core.quest.application.usecase;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.core.quest.application.port.CreateQuestContentPortOut;
import com.gomo.app.core.quest.application.port.FillQuestPoolPortIn;
import com.gomo.app.core.quest.application.port.ReadActiveParticipantPortOut;
import com.gomo.app.core.quest.application.port.ReadSubjectPortOut;
import com.gomo.app.core.quest.application.port.command.CreateQuestContentCommand;
import com.gomo.app.core.quest.application.port.dto.ActiveParticipantDto;
import com.gomo.app.core.quest.domain.model.pool.ProcessingStatus;
import com.gomo.app.core.quest.domain.model.pool.QuestPool;
import com.gomo.app.core.quest.domain.model.pool.SourceType;
import com.gomo.app.core.quest.domain.model.quest.Quest;
import com.gomo.app.core.quest.domain.model.quest.QuestContent;
import com.gomo.app.core.quest.domain.model.quest.QuestType;
import com.gomo.app.core.quest.domain.model.subject.SubjectName;
import com.gomo.app.core.quest.domain.repository.QuestPoolRepository;
import com.gomo.app.support.logging.AuditLog;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
class CreateQuestPoolUseCase implements FillQuestPoolPortIn {

	private final ReadActiveParticipantPortOut readActiveParticipantPortOut;
	private final ReadSubjectPortOut readSubjectPortOut;
	private final CreateQuestContentPortOut createQuestContentPortOut;
	private final QuestPoolRepository questPoolRepository;

	@AuditLog(action = "FILL_QUEST_POOL")
	@Override
	public void fillForActiveParticipants(LocalDate lastLoginDateOfTargets, QuestType questType, int limit) {
		List<ActiveParticipantDto> activeParticipants = readActiveParticipantPortOut.findAll(lastLoginDateOfTargets);
		for (ActiveParticipantDto activeParticipant : activeParticipants) {
			UUID participantId = activeParticipant.participantId();
			List<CreateQuestContentCommand.Subject> subjects = readSubjectPortOut.findAll(participantId).stream()
				.map(dto -> CreateQuestContentCommand.Subject.of(dto.participantId(), dto.subjectName(), dto.level())).toList();

			int existCount = (int)questPoolRepository.countByQuestParticipantIdAndProcessingStatus(participantId, ProcessingStatus.UNUSED);
			CreateQuestContentCommand command = CreateQuestContentCommand.of(participantId, subjects, questType.name(), limit - existCount);
			List<QuestPool> questPools = createQuestContentPortOut.create(command).stream()
				.map(dto -> {
					Quest quest = Quest.of(
						dto.participantId(),
						dto.subjectId(),
						SubjectName.of(dto.subjectName()),
						QuestType.valueOf(dto.questType()),
						QuestContent.of(dto.questContent())
					);

					return QuestPool.of(
						UUIDGenerator.generate(),
						quest,
						ProcessingStatus.UNUSED,
						SourceType.AI
					);
				}).toList();
			questPoolRepository.saveAll(questPools);
		}
	}
}
