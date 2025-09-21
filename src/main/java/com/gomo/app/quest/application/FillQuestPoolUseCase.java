package com.gomo.app.quest.application;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.quest.application.port.CreateQuestContentPortOut;
import com.gomo.app.quest.application.port.FillQuestPoolPortIn;
import com.gomo.app.quest.application.port.ReadActiveParticipantPortOut;
import com.gomo.app.quest.application.port.ReadSubjectPortOut;
import com.gomo.app.quest.application.port.command.CreateQuestContentCommand;
import com.gomo.app.quest.application.port.dto.ActiveParticipantDto;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.model.ProcessingStatus;
import com.gomo.app.quest.domain.model.Quest;
import com.gomo.app.quest.domain.model.QuestContent;
import com.gomo.app.quest.domain.model.QuestPool;
import com.gomo.app.quest.domain.model.QuestPoolId;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.model.SourceType;
import com.gomo.app.quest.domain.model.SubjectId;
import com.gomo.app.quest.domain.model.SubjectName;
import com.gomo.app.quest.domain.repository.QuestPoolRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
class FillQuestPoolUseCase implements FillQuestPoolPortIn {

	private final ReadActiveParticipantPortOut readActiveParticipantPortOut;
	private final ReadSubjectPortOut readSubjectPortOut;
	private final CreateQuestContentPortOut createQuestContentPortOut;
	private final QuestPoolRepository questPoolRepository;

	@Override
	public void fillForAllActiveParticipants(LocalDate lastLoginDateOfTargets, QuestType questType, int limit) {
		List<ActiveParticipantDto> activeParticipants = readActiveParticipantPortOut.findAll(lastLoginDateOfTargets);

		for (ActiveParticipantDto activeParticipant : activeParticipants) {
			UUID participantId = activeParticipant.participantId();
			List<CreateQuestContentCommand.Subject> subjects = readSubjectPortOut.findAll(participantId).stream()
				.map(dto -> CreateQuestContentCommand.Subject.of(
					dto.participantId(),
					dto.subjectName(),
					dto.level()
				)).toList();

			int existCount = (int)questPoolRepository.countByQuestParticipantIdAndProcessingStatus(ParticipantId.of(participantId), ProcessingStatus.UNUSED);
			CreateQuestContentCommand command = CreateQuestContentCommand.of(participantId, subjects, questType.name(), limit - existCount);
			List<QuestPool> questPools = createQuestContentPortOut.create(command).stream()
				.map(dto -> {
					Quest quest = Quest.of(
						ParticipantId.of(dto.participantId()),
						SubjectId.of(dto.subjectId()),
						SubjectName.of(dto.subjectName()),
						QuestType.valueOf(dto.questType()),
						QuestContent.of(dto.questContent())
					);

					return QuestPool.of(
						QuestPoolId.of(UUIDGenerator.generate()),
						quest,
						ProcessingStatus.UNUSED,
						SourceType.AI
					);
				}).toList();
			questPoolRepository.saveAll(questPools);
		}
	}
}
