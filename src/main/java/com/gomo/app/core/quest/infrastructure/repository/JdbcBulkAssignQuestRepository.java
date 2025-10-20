package com.gomo.app.core.quest.infrastructure.repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.gomo.app.common.arch.Adapter;
import com.gomo.app.common.util.UUIDConverter;
import com.gomo.app.core.quest.domain.model.assign.AssignQuest;
import com.gomo.app.core.quest.domain.repository.BulkAssignQuestRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Adapter
public class JdbcBulkAssignQuestRepository implements BulkAssignQuestRepository {

	private static final int BATCH_SIZE = 1000;
	private static final String GENERATOR_ID = "SYSTEM";

	private final JdbcTemplate jdbcTemplate;

	@Override
	public void saveAll(List<AssignQuest> assignQuests) {
		if (assignQuests == null || assignQuests.isEmpty()) {
			return;
		}

		String sql = "INSERT INTO assign_quest ("
			+ "id, is_confirmed, is_completed, proof, start_date_time, completed_date_time, "
			+ "participant_id, subject_id, subject_name, quest_type, content, "
			+ "display_order, "
			+ "created_at, created_by, last_modified_at, last_modified_by"
			+ ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		LocalDateTime now = LocalDateTime.now();
		jdbcTemplate.batchUpdate(sql, assignQuests, BATCH_SIZE,
			(PreparedStatement ps, AssignQuest quest) -> {
				ps.setObject(1, UUIDConverter.uuidToBytes(quest.getId()));
				ps.setBoolean(2, quest.isConfirmed());
				ps.setBoolean(3, quest.isCompleted());
				ps.setString(4, quest.getProof() != null ? quest.getProof().getUrl() : null);
				ps.setTimestamp(5, Timestamp.valueOf(quest.getStartDateTime()));
				if (quest.getCompletedDateTime() != null) {
					ps.setTimestamp(6, Timestamp.valueOf(quest.getCompletedDateTime()));
				} else {
					ps.setNull(6, Types.TIMESTAMP);
				}

				ps.setObject(7, UUIDConverter.uuidToBytes(quest.participantId()));
				ps.setObject(8, UUIDConverter.uuidToBytes(quest.subjectId()));
				ps.setString(9, quest.subjectName());
				ps.setString(10, quest.questType().name());
				ps.setString(11, quest.content());
				ps.setInt(12, quest.displayOrder());

				ps.setTimestamp(13, Timestamp.valueOf(now));
				ps.setString(14, GENERATOR_ID);
				ps.setTimestamp(15, Timestamp.valueOf(now));
				ps.setString(16, GENERATOR_ID);
			});
	}
}
