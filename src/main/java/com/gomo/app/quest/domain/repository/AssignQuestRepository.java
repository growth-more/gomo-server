package com.gomo.app.quest.domain.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gomo.app.quest.domain.model.AssignQuest;
import com.gomo.app.quest.domain.model.AssignQuestId;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.model.QuestType;

public interface AssignQuestRepository extends JpaRepository<AssignQuest, AssignQuestId> {

	@Query("select COUNT(a) from AssignQuest a " +
		"where a.quest.participantId = :participantId " +
		"and a.quest.type = :questType " +
		"and a.startDateTime between :startOfPeriod and :endOfPeriod")
	long countParticipatingQuestByQuestType(
		@Param("participantId") ParticipantId participantId,
		@Param("questType") QuestType questType,
		@Param("startOfPeriod") LocalDateTime startOfPeriod,
		@Param("endOfPeriod") LocalDateTime endOfPeriod
	);

	@Query("select COALESCE(MAX(a.displayOrder.displayOrder), 0) from AssignQuest a " +
		"where a.quest.participantId = :participantId " +
		"and a.quest.type = :questType " +
		"and a.startDateTime between :startOfPeriod and :endOfPeriod " +
		"and a.isCompleted = false")
	int findMaxDisplayOrderOfParticipatingQuest(
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

	@Query("select a from AssignQuest a where a.quest.participantId = :participantId " +
		"and a.quest.type = :questType " +
		"and a.isCompleted = false " +
		"and a.startDateTime between :startOfPeriod and :endOfPeriod " +
		"order by a.displayOrder.displayOrder asc")
	List<AssignQuest> findParticipatingQuestByQuestTypeWithoutCompleted(
		@Param("participantId") ParticipantId participantId,
		@Param("questType") QuestType questType,
		@Param("startOfPeriod") LocalDateTime startOfPeriod,
		@Param("endOfPeriod") LocalDateTime endOfPeriod
	);

	List<AssignQuest> findByQuestParticipantIdAndStartDateTimeBetween(ParticipantId participantId,
		LocalDateTime startOfPeriod, LocalDateTime endOfPeriod);

	@Modifying
	@Query("DELETE FROM AssignQuest a WHERE a.quest.participantId = :participantId")
	void deleteAllByParticipantId(ParticipantId participantId);
}
