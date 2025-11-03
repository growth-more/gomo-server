package com.gomo.app.core.quest.domain.model.participant;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gomo.app.core.quest.domain.model.quest.QuestType;
import com.gomo.app.core.quest.domain.exception.QuestConstraintViolationException;
import com.gomo.app.core.quest.domain.exception.code.QuestErrorCode;

@DisplayName("[Domain unit]: 퀘스트 참여자 엔티티 테스트")
public class ParticipantTest {

	@DisplayName("참여자의 퀘스트 수가 할당량을 초과하지 않는다.")
	@Test
	void not_exceed_quest_quota() {
		Participant participant = Participant.of(UUID.randomUUID(), QuestQuota.of(5, 5, 5));

		assertThatCode(() -> participant.validateQuestQuota(QuestType.DAILY, 4))
			.doesNotThrowAnyException();
	}

	@DisplayName("참여자의 퀘스트 수가 할당량을 초과한다.")
	@Test
	void exceed_quest_quota() {
		Participant participant = Participant.of(UUID.randomUUID(), QuestQuota.of(5, 5, 5));

		assertThatThrownBy(() -> participant.validateQuestQuota(QuestType.DAILY, 5))
			.isInstanceOf(QuestConstraintViolationException.class)
			.hasMessageContaining(QuestErrorCode.EXCEED_QUOTA.getMessage());
	}
}
