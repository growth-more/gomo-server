package com.gomo.app.quest.domain.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gomo.app.quest.domain.model.AssignQuest;
import com.gomo.app.quest.domain.model.AssignQuestId;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.model.QuestHistory;
import com.gomo.app.quest.domain.model.QuestType;

public interface AssignQuestRepository extends JpaRepository<AssignQuest, AssignQuestId> {

	long countByQuestParticipantIdAndQuestTypeAndStartDateTimeBetween(
		ParticipantId participantId,
		QuestType questType,
		LocalDateTime startDateTimeStart,
		LocalDateTime startDateTimeEnd
	);

	List<QuestHistory> findHistories(
		ParticipantId participantId,
		QuestType questType,
		Long lastElementId
	);
}
