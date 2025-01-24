package com.gomo.app.quest.domain.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gomo.app.quest.domain.model.AssignQuest;
import com.gomo.app.quest.domain.model.AssignQuestId;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.model.QuestHistory;
import com.gomo.app.quest.domain.model.QuestType;

public interface AssignQuestRepository extends JpaRepository<AssignQuest, AssignQuestId> {

	// @Query("")
	// List<AssignQuest> findActiveQuest(
	// 	LocalDate startDate,
	// 	QuestType questType
	// );
	//
	// @Query("")
	// List<QuestHistory> findHistories(
	// 	ParticipantId participantId,
	// 	QuestType questType,
	// 	LocalDate completedDate,
	// 	Long lastElementId
	// );
}
