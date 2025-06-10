package com.gomo.app.quest.integration;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gomo.app.common.IntegrationTestBase;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.model.RepeatQuest;
import com.gomo.app.quest.domain.repository.RepeatQuestRepository;
import com.gomo.app.quest.fixture.RepeatQuestFixture;

@DisplayName("[Domain integration]: 반복 퀘스트 DB 접근 테스트")
public class RepeatQuestRepositoryTest extends IntegrationTestBase {

	@Autowired
	RepeatQuestRepository sut;

	@Autowired
	RepeatQuestRepository repeatQuestRepository;
	RepeatQuest repeatQuest1;
	RepeatQuest repeatQuest2;
	UUID participantId;

	@BeforeEach
	public void setUp() {
		participantId = UUID.randomUUID();
		repeatQuest1 = RepeatQuestFixture.repeatQuest(participantId, 1);
		repeatQuest2 = RepeatQuestFixture.repeatQuest(participantId, 2);
		repeatQuestRepository.saveAll(List.of(repeatQuest1, repeatQuest2));
	}

	@AfterEach
	void tearDown() {
		repeatQuestRepository.deleteAllInBatch();
	}

	@DisplayName("반복 퀘스트 개수를 조회한다.")
	@Test
	void count_repeat_quest() {
		long actual = sut.countByQuestParticipantIdAndQuestType(
			ParticipantId.of(participantId),
			QuestType.DAILY
		);

		assertThat(actual).isEqualTo(2L);
	}

	@DisplayName("반복 퀘스트의 마지막 정렬 번호를 조회한다.")
	@Test
	void find_max_order_repeat_quest() {
		int actual = sut.findMaxDisplayOrderByQuestType(
			ParticipantId.of(participantId),
			QuestType.DAILY
		);

		assertThat(actual).isEqualTo(2);
	}

	@DisplayName("퀘스트 타입에 따른 반복 퀘스트 목록을 조회한다.")
	@Test
	void find_repeat_quests() {
		List<RepeatQuest> actual = sut.findRepeatQuestsByQuestType(
			ParticipantId.of(participantId),
			QuestType.DAILY
		);

		assertThat(actual.size()).isEqualTo(2);
	}
}
