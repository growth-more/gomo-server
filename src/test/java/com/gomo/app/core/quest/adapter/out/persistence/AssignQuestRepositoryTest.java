package com.gomo.app.core.quest.adapter.out.persistence;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.core.quest.domain.model.assign.AssignQuest;
import com.gomo.app.core.quest.domain.model.assign.CompletionProof;
import com.gomo.app.core.quest.domain.model.quest.QuestType;
import com.gomo.app.core.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.core.quest.fixture.AssignQuestFixture;
import com.gomo.app.test.IntegrationTest;

@DisplayName("[Domain integration]: 할당 퀘스트 DB 접근 테스트")
@IntegrationTest
public class AssignQuestRepositoryTest {

	@Autowired
	private AssignQuestRepository sut;

	@Autowired
	private AssignQuestRepository assignQuestRepository;
	private UUID participantId;

	@BeforeEach
	public void setUp() {
		participantId = UUID.randomUUID();

		LocalDateTime startDateTime1 = LocalDateTime.of(2025, 1, 21, 10, 0);
		AssignQuest notConfirmed = AssignQuestFixture.create(participantId, false, startDateTime1, 1);
		AssignQuest confirmed = AssignQuestFixture.create(participantId, true, startDateTime1, 2);

		LocalDateTime completedDateTime1 = LocalDateTime.of(2025, 1, 21, 11, 0);
		AssignQuest completed1 = AssignQuestFixture.create(participantId, true, CompletionProof.of("proof"), startDateTime1, completedDateTime1);

		LocalDateTime startDateTime2 = LocalDateTime.of(2025, 1, 20, 10, 0);
		LocalDateTime completedDateTime2 = LocalDateTime.of(2025, 1, 20, 11, 0);
		AssignQuest completed2 = AssignQuestFixture.create(participantId, true, CompletionProof.of("proof"), startDateTime2, completedDateTime2);
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
			participantId,
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
			participantId,
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
			participantId,
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
			participantId,
			QuestType.DAILY,
			LocalDateTime.of(2025, 1, 21, 0, 0, 0),
			LocalDateTime.of(2025, 1, 21, 23, 59, 59)
		);

		assertThat(actual.size()).isEqualTo(2);
		assertThat(actual).extracting("isCompleted").containsOnly(false);
	}

	@DisplayName("완료한 퀘스트 목록을 퀘스트 완료일 기준으로 조회한다.")
	@Test
	void find_completed_quest_between_completed_date_time() {
		LocalDateTime startDateTime = LocalDateTime.of(2025, 1, 1, 0, 0, 0);
		LocalDateTime endDateTime = LocalDateTime.of(2025, 1, 23, 23, 59, 59);
		List<AssignQuest> actual = sut.findByQuestParticipantIdAndCompletedDateTimeBetween(participantId, startDateTime, endDateTime);
		assertThat(actual.size()).isEqualTo(2);
	}

	@DisplayName("완료하지 못한 퀘스트 목록을 퀘스트 시작일 기준으로 조회한다.")
	@Test
	void find_not_completed_quest_between_start_date_time() {
		LocalDateTime startDateTime = LocalDateTime.of(2025, 1, 1, 0, 0, 0);
		LocalDateTime endDateTime = LocalDateTime.of(2025, 1, 23, 23, 59, 59);
		List<AssignQuest> actual = sut.findByQuestParticipantIdAndStartDateTimeBetweenAndIsCompletedFalse(participantId, startDateTime, endDateTime);
		assertThat(actual.size()).isEqualTo(2);
	}

	@DisplayName("특정사용자의 퀘스트를 모두 삭제한다.")
	@Transactional
	@Test
	void delete_all_assign_quests() {
		sut.deleteAllByParticipantId(participantId);
		assertThat(sut.countParticipatingQuestByQuestType(participantId, null, null, null)).isEqualTo(0);
	}
}
