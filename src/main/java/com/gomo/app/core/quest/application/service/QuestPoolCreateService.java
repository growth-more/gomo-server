package com.gomo.app.core.quest.application.service;

import java.util.List;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.logging.AuditLog;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.core.quest.application.port.command.CreateQuestContentCommand;
import com.gomo.app.core.quest.application.port.command.CreateQuestPoolCommand;
import com.gomo.app.core.quest.application.port.in.QuestPoolCreator;
import com.gomo.app.core.quest.application.port.out.QuestContentCreator;
import com.gomo.app.core.quest.domain.model.pool.ProcessingStatus;
import com.gomo.app.core.quest.domain.model.pool.QuestPool;
import com.gomo.app.core.quest.domain.model.pool.SourceType;
import com.gomo.app.core.quest.domain.model.quest.Quest;
import com.gomo.app.core.quest.domain.model.quest.QuestContent;
import com.gomo.app.core.quest.domain.model.quest.QuestType;
import com.gomo.app.core.quest.domain.model.subject.SubjectName;
import com.gomo.app.core.quest.domain.repository.QuestPoolRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional
@ApplicationService
class QuestPoolCreateService implements QuestPoolCreator {

	private final QuestContentCreator questContentCreator;
	private final QuestPoolRepository questPoolRepository;

	@AuditLog(action = "QUEST_POOL_CREATE")
	@Override
	public void create(CreateQuestPoolCommand command) {
		UUID participantId = command.participantId();
		String questType = command.questType();
		int existCount = (int)questPoolRepository.countByQuestParticipantIdAndQuestTypeAndProcessingStatus(
			participantId,
			QuestType.valueOf(questType),
			ProcessingStatus.UNUSED
		);

		int limit = command.limit();
		if (existCount >= limit) {
			log.debug("Skipped creating quest pool. Quest pool is already full with participant id={}, quest type={}, limit={}", participantId, questType, limit);
			return;
		}

		List<CreateQuestContentCommand.Subject> subjects = command.subjects().stream()
			.map(subject -> CreateQuestContentCommand.Subject.of(subject.id(), subject.name(), subject.level()))
			.toList();
		int createCount = limit - existCount;
		List<QuestPool> questPools = questContentCreator.create(CreateQuestContentCommand.of(participantId, subjects, questType, createCount)).stream()
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

		if (questPools.isEmpty()) {
			log.debug("No quest pools created for participant id={}", participantId);
			return;
		}
		questPoolRepository.saveAll(questPools);
	}
}
