package com.gomo.app.quest.domain.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.model.ProcessingStatus;
import com.gomo.app.quest.domain.model.QuestPool;
import com.gomo.app.quest.domain.model.QuestPoolId;

public interface QuestPoolRepository extends JpaRepository<QuestPool, QuestPoolId> {

	long countByQuestParticipantIdAndProcessingStatus(ParticipantId participantId, ProcessingStatus processingStatus);

	List<QuestPool> findTopByQuestParticipantIdAndProcessingStatus(ParticipantId participantId, ProcessingStatus processingStatus, Pageable pageable);
}
