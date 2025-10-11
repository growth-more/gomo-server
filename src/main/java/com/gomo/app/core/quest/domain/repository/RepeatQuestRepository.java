package com.gomo.app.core.quest.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gomo.app.core.quest.domain.model.quest.QuestType;
import com.gomo.app.core.quest.domain.model.repeat.RepeatQuest;

public interface RepeatQuestRepository extends JpaRepository<RepeatQuest, UUID> {

	long countByQuestParticipantIdAndQuestType(UUID participantId, QuestType questType);

	@Query("select COALESCE(MAX(r.displayOrder.displayOrder), 0) from RepeatQuest r " +
		"where r.quest.participantId = :participantId " +
		"and r.quest.type = :questType")
	int findMaxDisplayOrderByQuestType(
		@Param("participantId") UUID participantId,
		@Param("questType") QuestType questType
	);

	@Query("select r from RepeatQuest r " +
		"where r.quest.participantId = :participantId " +
		"and r.quest.type = :questType " +
		"order by r.displayOrder.displayOrder asc")
	List<RepeatQuest> findRepeatQuestsByQuestType(
		@Param("participantId") UUID participantId,
		@Param("questType") QuestType questType
	);

	@Modifying
	@Query("DELETE FROM RepeatQuest r WHERE r.quest.participantId = :participantId")
	void deleteAllByParticipantId(UUID participantId);

}
