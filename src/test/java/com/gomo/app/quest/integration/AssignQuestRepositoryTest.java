package com.gomo.app.quest.integration;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gomo.app.common.IntegrationTestBase;
import com.gomo.app.quest.domain.model.AssignQuest;
import com.gomo.app.quest.domain.model.CompletionProof;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.quest.fixture.AssignQuestFixture;

@DisplayName("[Domain integration]: 할당 퀘스트 DB 접근 테스트")
public class AssignQuestRepositoryTest extends IntegrationTestBase {

	@Autowired
	AssignQuestRepository sut;

	@Autowired
	private AssignQuestRepository assignQuestRepository;
	AssignQuest notConfirmed;
	AssignQuest confirmed;
	AssignQuest completed1;
	AssignQuest completed2;

	@BeforeEach
	public void setUp() {
		UUID participantId = UUID.randomUUID();
		notConfirmed = AssignQuestFixture.assignQuest(participantId, false, LocalDateTime.of(2025, 1, 21, 10, 0), 1);
		confirmed = AssignQuestFixture.assignQuest(participantId, true, LocalDateTime.of(2025, 1, 21, 10, 0), 2);
		completed1 = AssignQuestFixture.assignQuest(participantId, true, CompletionProof.of("completed"),
			LocalDateTime.of(2025, 1, 21, 10, 0));
		completed2 = AssignQuestFixture.assignQuest(participantId, true, CompletionProof.of("completed"),
			LocalDateTime.of(2025, 1, 20, 0, 0));
		assignQuestRepository.saveAll(List.of(notConfirmed, confirmed, completed1, completed2));
	}

	@AfterEach
	void tearDown() {
		assignQuestRepository.deleteAllInBatch();
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

	@DisplayName("현재 참여중인 퀘스트(당일 완료 퀘스트 제외)의 마지막 정렬 번호를 조회한다.")
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
