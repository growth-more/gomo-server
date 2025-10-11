package com.gomo.app.core.quest.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gomo.app.core.quest.domain.model.pool.ProcessingStatus;
import com.gomo.app.core.quest.domain.model.pool.QuestPool;
import com.gomo.app.core.quest.domain.model.quest.QuestType;

public interface QuestPoolRepository extends JpaRepository<QuestPool, UUID> {

	long countByQuestParticipantIdAndProcessingStatus(UUID participantId, ProcessingStatus processingStatus);

	List<QuestPool> findTopByQuestParticipantIdAndQuestTypeAndProcessingStatus(UUID participantId, QuestType type, ProcessingStatus processingStatus,
		Pageable pageable);
}
