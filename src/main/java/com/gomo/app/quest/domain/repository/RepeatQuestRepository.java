package com.gomo.app.quest.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.model.RepeatQuest;
import com.gomo.app.quest.domain.model.RepeatQuestId;

public interface RepeatQuestRepository extends JpaRepository<RepeatQuest, RepeatQuestId> {

	List<RepeatQuest> findByQuestParticipantId(ParticipantId participantId);

	long countByQuestParticipantIdAndQuestType(ParticipantId participantId, QuestType questType);

	@Query("select COALESCE(MAX(r.displayOrder.displayOrder), 0) from RepeatQuest r " +
		"where r.quest.participantId = :participantId " +
		"and r.quest.type = :questType")
	int findMaxDisplayOrderByQuestType(
		@Param("participantId") ParticipantId participantId,
		@Param("questType") QuestType questType
	);

	@Query("select r from RepeatQuest r " +
		"where r.quest.participantId = :participantId " +
		"and r.quest.type = :questType " +
		"order by r.displayOrder.displayOrder asc")
	List<RepeatQuest> findRepeatQuestsByQuestType(
		@Param("participantId") ParticipantId participantId,
		@Param("questType") QuestType questType
	);

	@Modifying
	@Query("DELETE FROM RepeatQuest r WHERE r.quest.participantId =: participantId")
	void deleteAllByParticipantId(ParticipantId participantId);

}
