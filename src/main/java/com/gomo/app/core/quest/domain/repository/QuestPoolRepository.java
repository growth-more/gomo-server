package com.gomo.app.core.quest.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gomo.app.core.quest.domain.model.pool.ProcessingStatus;
import com.gomo.app.core.quest.domain.model.pool.QuestPool;
import com.gomo.app.core.quest.domain.model.pool.SourceType;
import com.gomo.app.core.quest.domain.model.quest.QuestType;

public interface QuestPoolRepository extends JpaRepository<QuestPool, UUID> {

	long countByQuestParticipantIdAndQuestTypeAndProcessingStatus(UUID participantId, QuestType questType, ProcessingStatus processingStatus);

	Optional<QuestPool> findFirstByQuestParticipantIdAndQuestTypeAndSourceTypeAndProcessingStatus(UUID questParticipantId, QuestType questType, SourceType sourceType,
		ProcessingStatus processingStatus);

	List<QuestPool> findByQuestParticipantIdAndQuestTypeAndProcessingStatus(UUID participantId, QuestType type, ProcessingStatus processingStatus, Pageable pageable);
}
