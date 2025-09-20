package com.gomo.app.quest.application;

import java.util.List;
import java.util.UUID;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.quest.application.port.CreateQuestContentPort;
import com.gomo.app.quest.application.port.ReadActiveParticipantPort;
import com.gomo.app.quest.application.port.ReadSubjectPort;
import com.gomo.app.quest.application.port.command.CreateQuestContentCommand;
import com.gomo.app.quest.application.port.command.ReadSubjectCommand;
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
public class FillQuestPoolUseCase {

	private final ReadActiveParticipantPort readActiveParticipantPort;
	private final ReadSubjectPort readSubjectPort;
	private final CreateQuestContentPort createQuestContentPort;
	private final QuestPoolRepository questPoolRepository;

	public void fillForAllActiveParticipants(QuestType questType, int countPerParticipant) {
		List<ActiveParticipantDto> activeParticipants = readActiveParticipantPort.findAll();

		for (ActiveParticipantDto activeParticipant : activeParticipants) {
			UUID participantId = activeParticipant.participantId();
			List<CreateQuestContentCommand.Subject> subjects = readSubjectPort.findAll(ReadSubjectCommand.of(participantId)).stream()
				.map(dto -> CreateQuestContentCommand.Subject.of(
					dto.participantId(),
					dto.subjectName(),
					dto.level()
				)).toList();

			CreateQuestContentCommand command = CreateQuestContentCommand.of(participantId, subjects, questType.name(), countPerParticipant);
			List<QuestPool> questPools = createQuestContentPort.create(command).stream()
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
