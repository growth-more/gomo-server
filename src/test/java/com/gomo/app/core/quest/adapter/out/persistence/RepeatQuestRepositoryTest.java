package com.gomo.app.core.quest.adapter.out.persistence;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.core.quest.domain.model.quest.QuestType;
import com.gomo.app.core.quest.domain.model.repeat.RepeatQuest;
import com.gomo.app.core.quest.domain.repository.RepeatQuestRepository;
import com.gomo.app.core.quest.fixture.RepeatQuestFixture;
import com.gomo.app.test.IntegrationTest;

@DisplayName("[Domain integration]: 반복 퀘스트 DB 접근 테스트")
@IntegrationTest
public class RepeatQuestRepositoryTest {

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
		repeatQuest1 = RepeatQuestFixture.create(participantId, 1);
		repeatQuest2 = RepeatQuestFixture.create(participantId, 2);
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
			participantId,
			QuestType.DAILY
		);

		assertThat(actual).isEqualTo(2L);
	}

	@DisplayName("반복 퀘스트의 마지막 정렬 번호를 조회한다.")
	@Test
	void find_max_order_repeat_quest() {
		int actual = sut.findMaxDisplayOrderByQuestType(
			participantId,
			QuestType.DAILY
		);

		assertThat(actual).isEqualTo(2);
	}

	@DisplayName("퀘스트 타입에 따른 반복 퀘스트 목록을 조회한다.")
	@Test
	void find_repeat_quests() {
		List<RepeatQuest> actual = sut.findRepeatQuestsByQuestType(
			participantId,
			QuestType.DAILY
		);

		assertThat(actual.size()).isEqualTo(2);
	}

	@DisplayName("특정 사용자의 반복퀘스트를 삭제한다.")
	@Transactional
	@Test
	void delete_all_repeat_quest() {

		sut.deleteAllByParticipantId(participantId);

		assertThat(sut.countByQuestParticipantIdAndQuestType(participantId, QuestType.DAILY)).isZero();
		assertThat(sut.countByQuestParticipantIdAndQuestType(participantId, QuestType.WEEKLY)).isZero();
		assertThat(sut.countByQuestParticipantIdAndQuestType(participantId, QuestType.MONTHLY)).isZero();
	}
}
