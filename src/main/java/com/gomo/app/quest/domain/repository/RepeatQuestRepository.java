package com.gomo.app.quest.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.model.RepeatQuest;
import com.gomo.app.quest.domain.model.RepeatQuestId;

public interface RepeatQuestRepository extends JpaRepository<RepeatQuest, RepeatQuestId> {

	Optional<RepeatQuest> findById(RepeatQuestId id);

	List<RepeatQuest> findAllByQuestParticipantIdAndQuestType(ParticipantId participantId, QuestType questType);

	Integer findLastOrderByQuestParticipantIdAndQuestType(ParticipantId participantId, QuestType questType);
}
