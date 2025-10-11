package com.gomo.app.core.quest.domain.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gomo.app.core.quest.domain.model.assign.AssignQuest;
import com.gomo.app.core.quest.domain.model.quest.QuestType;

public interface AssignQuestRepository extends JpaRepository<AssignQuest, UUID> {

	@Query("select COUNT(a) from AssignQuest a " +
		"where a.quest.participantId = :participantId " +
		"and a.quest.type = :questType " +
		"and a.startDateTime between :startOfPeriod and :endOfPeriod")
	long countParticipatingQuestByQuestType(
		@Param("participantId") UUID participantId,
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
		@Param("participantId") UUID participantId,
		@Param("questType") QuestType questType,
		@Param("startOfPeriod") LocalDateTime startOfPeriod,
		@Param("endOfPeriod") LocalDateTime endOfPeriod
	);

	@Query("select a from AssignQuest a where a.quest.participantId = :participantId " +
		"and a.quest.type = :questType " +
		"and a.startDateTime between :startOfPeriod and :endOfPeriod " +
		"order by a.displayOrder.displayOrder asc")
	List<AssignQuest> findParticipatingQuestByQuestType(
		@Param("participantId") UUID participantId,
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
		@Param("participantId") UUID participantId,
		@Param("questType") QuestType questType,
		@Param("startOfPeriod") LocalDateTime startOfPeriod,
		@Param("endOfPeriod") LocalDateTime endOfPeriod
	);

	List<AssignQuest> findByQuestParticipantIdAndCompletedDateTimeBetween(UUID participantId,
		LocalDateTime completedDateTimeAfter, LocalDateTime completedDateTimeBefore);

	List<AssignQuest> findByQuestParticipantIdAndStartDateTimeBetweenAndIsCompletedFalse(UUID participantId,
		LocalDateTime startDateTimeAfter, LocalDateTime startDateTimeBefore);

	@Modifying
	@Query("DELETE FROM AssignQuest a WHERE a.quest.participantId = :participantId")
	void deleteAllByParticipantId(UUID participantId);
}
