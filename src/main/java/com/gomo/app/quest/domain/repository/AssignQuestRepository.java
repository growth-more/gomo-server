package com.gomo.app.quest.domain.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gomo.app.quest.domain.model.AssignQuest;
import com.gomo.app.quest.domain.model.AssignQuestId;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.model.QuestType;

public interface AssignQuestRepository extends JpaRepository<AssignQuest, AssignQuestId> {

	@Query("select COUNT(a) from AssignQuest a where a.quest.participantId = :participantId " +
		"and a.quest.type = :questType " +
		"and a.startDateTime between :startOfPeriod and :endOfPeriod")
	long countParticipatingQuestByQuestType(
		@Param("participantId") ParticipantId participantId,
		@Param("questType") QuestType questType,
		@Param("startOfPeriod") LocalDateTime startOfPeriod,
		@Param("endOfPeriod") LocalDateTime endOfPeriod
	);

	@Query("select a from AssignQuest a where a.quest.participantId = :participantId " +
		"and a.quest.type = :questType " +
		"and a.startDateTime between :startOfPeriod and :endOfPeriod " +
		"order by a.displayOrder.displayOrder asc")
	List<AssignQuest> findParticipatingQuestByQuestType(
		@Param("participantId") ParticipantId participantId,
		@Param("questType") QuestType questType,
		@Param("startOfPeriod") LocalDateTime startOfPeriod,
		@Param("endOfPeriod") LocalDateTime endOfPeriod
	);

	@Query(value = "select a.* from assign_quest a " +
			"where a.participant_id = UNHEX(REPLACE(:participantId, '-', '')) " +
			"and a.quest_type = :questType " +
			"and a.start_date_time not between :startOfPeriod and :endOfPeriod " +
			"and (UNHEX(REPLACE(:lastElementId, '-', '')) is null or a.id > UNHEX(REPLACE(:lastElementId, '-', ''))) " +
			"order by a.start_date_time desc " +
			"limit :size", nativeQuery = true)
	List<AssignQuest> findHistoryQuestByQuestType(
		@Param("participantId") String participantId,
		@Param("questType") String questType,
		@Param("startOfPeriod") LocalDateTime startOfPeriod,
		@Param("endOfPeriod") LocalDateTime endOfPeriod,
		@Param("lastElementId") String lastElementId,
		@Param("size") int size
	);
}
