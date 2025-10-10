package com.gomo.app.support.evententry.infrastructure.adapter;

import org.springframework.jdbc.core.JdbcTemplate;

import com.gomo.app.common.arch.Adapter;
import com.gomo.app.support.evententry.domain.repository.ProcessedEventEntryRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Adapter
class JdbcProcessedEventEntryRepository implements ProcessedEventEntryRepository {

	private final JdbcTemplate jdbcTemplate;

	@Override
	public void save(String eventEntryId, String consumerName) {
		String insertSql = "INSERT INTO processed_event_entry (event_entry_id, consumer_name) VALUES (?, ?)";
		jdbcTemplate.update(insertSql, eventEntryId, consumerName);
	}

	@Override
	public boolean existsByEventEntryIdAndConsumerName(String eventEntryId, String consumerName) {
		String selectSql = "SELECT 1 FROM processed_event_entry WHERE event_entry_id = ? AND consumer_name = ? FOR UPDATE";
		return !jdbcTemplate.query(selectSql, (rs, rowNum) -> rs.getInt(1), eventEntryId, consumerName).isEmpty();
	}
}
