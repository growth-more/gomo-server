package com.gomo.app.core.quest.domain.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gomo.app.core.quest.domain.model.participant.ParticipantId;
import com.gomo.app.core.quest.domain.model.pool.ProcessingStatus;
import com.gomo.app.core.quest.domain.model.pool.QuestPool;
import com.gomo.app.core.quest.domain.model.pool.QuestPoolId;
import com.gomo.app.core.quest.domain.model.quest.QuestType;

public interface QuestPoolRepository extends JpaRepository<QuestPool, QuestPoolId> {

	long countByQuestParticipantIdAndProcessingStatus(ParticipantId participantId, ProcessingStatus processingStatus);

	List<QuestPool> findTopByQuestParticipantIdAndQuestTypeAndProcessingStatus(ParticipantId participantId, QuestType type, ProcessingStatus processingStatus,
		Pageable pageable);
}
