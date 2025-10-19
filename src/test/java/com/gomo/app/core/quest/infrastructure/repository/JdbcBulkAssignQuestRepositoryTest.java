package com.gomo.app.core.quest.infrastructure.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.util.UUIDConverter;
import com.gomo.app.core.quest.domain.model.assign.AssignQuest;
import com.gomo.app.core.quest.fixture.AssignQuestFixture;
import com.gomo.app.test.IntegrationTest;

@DisplayName("[Domain integration]: 할당 퀘스트 DB 벌크 테스트")
@IntegrationTest
@Transactional
class JdbcBulkAssignQuestRepositoryTest {

	@Autowired
	private JdbcBulkAssignQuestRepository bulkAssignQuestRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@DisplayName("AssignQuest 리스트를 정상적으로 데이터베이스에 벌크 삽입한다.")
	@Test
	void save_all_success() {
		AssignQuest assignQuest1 = AssignQuestFixture.create();
		AssignQuest assignQuest2 = AssignQuestFixture.create();
		List<AssignQuest> assignQuests = List.of(assignQuest1, assignQuest2);

		bulkAssignQuestRepository.saveAll(assignQuests);

		Integer savedCount = jdbcTemplate.queryForObject("SELECT count(*) FROM assign_quest", Integer.class);
		assertThat(savedCount).isEqualTo(2);
	}

	@DisplayName("AssignQuest의 완료일이 비어있다면 null을 삽입한다.")
	@Test
	void save_all_with_empty_completed_date() {
		AssignQuest assignQuest = AssignQuestFixture.create(LocalDateTime.now(), null);

		bulkAssignQuestRepository.saveAll(List.of(assignQuest));

		Map<String, Object> actual = jdbcTemplate.queryForMap("SELECT * FROM assign_quest WHERE id = ?", UUIDConverter.uuidToBytes(assignQuest.getId()));
		assertThat(actual.get("completed_date_time")).isNull();
	}

	@DisplayName("AssignQuest의 완료일이 있다면 해당 날짜를 삽입한다.")
	@Test
	void save_all_with_completed_date() {
		LocalDateTime now = LocalDateTime.now();
		AssignQuest assignQuest = AssignQuestFixture.create(now, now);

		bulkAssignQuestRepository.saveAll(List.of(assignQuest));

		Map<String, Object> actual = jdbcTemplate.queryForMap("SELECT * FROM assign_quest WHERE id = ?", UUIDConverter.uuidToBytes(assignQuest.getId()));
		assertThat(actual.get("completed_date_time")).isEqualTo(now);
	}

	@DisplayName("비어 있는 리스트를 전달하면 아무 작업도 수행하지 않는다.")
	@Test
	void save_all_with_empty_list() {
		List<AssignQuest> assignQuests = List.of();

		bulkAssignQuestRepository.saveAll(assignQuests);

		Integer savedCount = jdbcTemplate.queryForObject("SELECT count(*) FROM assign_quest", Integer.class);
		assertThat(savedCount).isZero();
	}

	@DisplayName("null 전달하면 아무 작업도 수행하지 않는다.")
	@Test
	void save_all_with_null() {
		bulkAssignQuestRepository.saveAll(null);

		Integer savedCount = jdbcTemplate.queryForObject("SELECT count(*) FROM assign_quest", Integer.class);
		assertThat(savedCount).isZero();
	}
}
