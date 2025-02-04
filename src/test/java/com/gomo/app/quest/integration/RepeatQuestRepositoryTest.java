package com.gomo.app.quest.integration;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gomo.app.common.IntegrationTestBase;
import com.gomo.app.quest.common.dataprovider.RepeatQuestDataProvider;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.model.RepeatQuest;
import com.gomo.app.quest.domain.repository.RepeatQuestRepository;

@DisplayName("[Domain integration]: 반복 퀘스트 DB 접근 테스트")
public class RepeatQuestRepositoryTest extends IntegrationTestBase {

	@Autowired
	RepeatQuestRepository sut;

	@Autowired
	RepeatQuestDataProvider repeatQuestDataProvider;
	RepeatQuest firstOrderDailyRepeatQuest;
	RepeatQuest secondOrderDailyRepeatQuest;

	@BeforeEach
	public void setUp() {
		firstOrderDailyRepeatQuest = repeatQuestDataProvider.firstOrderDaily();
		secondOrderDailyRepeatQuest = repeatQuestDataProvider.secondOrderDaily();
	}

	@DisplayName("반복 퀘스트 개수를 조회한다.")
	@Test
	void count_repeat_quest() {
		long actual = sut.countByQuestParticipantIdAndQuestType(
			firstOrderDailyRepeatQuest.getQuest().getParticipantId(),
			QuestType.DAILY
		);

		assertThat(actual).isEqualTo(2L);
	}

	@DisplayName("반복 퀘스트의 마지막 정렬 번호를 조회한다.")
	@Test
	void find_max_order_repeat_quest() {
		int actual = sut.findMaxDisplayOrderByRepeatQuest(
			firstOrderDailyRepeatQuest.getQuest().getParticipantId(),
			QuestType.DAILY
		);

		assertThat(actual).isEqualTo(2);
	}

	@DisplayName("퀘스트 타입에 따른 반복 퀘스트 목록을 조회한다.")
	@Test
	void find_repeat_quests() {
		List<RepeatQuest> actual = sut.findRepeatQuestsByQuestType(
			firstOrderDailyRepeatQuest.getQuest().getParticipantId(),
			QuestType.DAILY
		);

		assertThat(actual.size()).isEqualTo(2);
	}
}
