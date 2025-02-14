package com.gomo.app.quest.integration;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gomo.app.common.IntegrationTestBase;
import com.gomo.app.quest.common.dataprovider.AssignQuestDataProvider;
import com.gomo.app.quest.domain.model.AssignQuest;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.repository.AssignQuestRepository;

@DisplayName("[Domain integration]: 할당 퀘스트 DB 접근 테스트")
public class AssignQuestRepositoryTest extends IntegrationTestBase {

	@Autowired
	AssignQuestRepository sut;

	@Autowired
	private AssignQuestDataProvider assignQuestDataProvider;
	AssignQuest notConfirmed;
	AssignQuest confirmed;
	AssignQuest completedJava;
	AssignQuest completedSpring;

	@BeforeEach
	public void setUp() {
		notConfirmed = assignQuestDataProvider.notConfirmed();
		confirmed = assignQuestDataProvider.confirmed();
		completedJava = assignQuestDataProvider.completedJava();
		completedSpring = assignQuestDataProvider.completedSpring();
	}

	@DisplayName("현재 참여중인 퀘스트 개수를 조회한다.")
	@Test
	void count_participating_quest() {
		long actual = sut.countParticipatingQuestByQuestType(
			notConfirmed.getQuest().getParticipantId(),
			QuestType.DAILY,
			LocalDateTime.of(2025, 1, 21, 0, 0, 0),
			LocalDateTime.of(2025, 1, 21, 23, 59, 59)
		);

		assertThat(actual).isEqualTo(3L);
	}

	@DisplayName("현재 참여중인 퀘스트의 마지막 정렬 번호를 조회한다.")
	@Test
	void find_max_order_participating_quest() {
		int actual = sut.findMaxDisplayOrderOfParticipatingQuest(
			notConfirmed.getQuest().getParticipantId(),
			QuestType.DAILY,
			LocalDateTime.of(2025, 1, 21, 0, 0, 0),
			LocalDateTime.of(2025, 1, 21, 23, 59, 59)
		);

		assertThat(actual).isEqualTo(2);
	}

	@DisplayName("현재 참여중인 퀘스트 목록을 조회한다.")
	@Test
	void find_participating_quests() {
		List<AssignQuest> actual = sut.findParticipatingQuestByQuestType(
			notConfirmed.getQuest().getParticipantId(),
			QuestType.DAILY,
			LocalDateTime.of(2025, 1, 21, 0, 0, 0),
			LocalDateTime.of(2025, 1, 21, 23, 59, 59)
		);

		assertThat(actual.size()).isEqualTo(3);
	}

	@DisplayName("완료한 퀘스트를 제외하고, 현재 참여중인 퀘스트 목록을 조회한다.")
	@Test
	void find_participating_quests_without_completed() {
		List<AssignQuest> actual = sut.findParticipatingQuestByQuestTypeWithoutCompleted(
			notConfirmed.getQuest().getParticipantId(),
			QuestType.DAILY,
			LocalDateTime.of(2025, 1, 21, 0, 0, 0),
			LocalDateTime.of(2025, 1, 21, 23, 59, 59)
		);

		assertThat(actual.size()).isEqualTo(2);
		assertThat(actual).extracting("isCompleted").containsOnly(false);
	}
}
