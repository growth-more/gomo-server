package com.gomo.app.support.messagebroker.adapter.out.repository;

import org.springframework.jdbc.core.JdbcTemplate;

import com.gomo.app.common.arch.Adapter;
import com.gomo.app.support.messagebroker.domain.repository.ProcessedDirectEventRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Adapter
class JdbcProcessedDirectEventRepository implements ProcessedDirectEventRepository {

	private final JdbcTemplate jdbcTemplate;

	@Override
	public void save(String directEventId, String consumerName) {
		String insertSql = "INSERT INTO processed_direct_event (direct_event_id, consumer_name) VALUES (?, ?)";
		jdbcTemplate.update(insertSql, directEventId, consumerName);
	}

	@Override
	public boolean existsByDirectEventIdAndConsumerName(String directEventId, String consumerName) {
		String selectSql = "SELECT 1 FROM processed_direct_event WHERE direct_event_id = ? AND consumer_name = ? FOR UPDATE";
		return !jdbcTemplate.query(selectSql, (rs, rowNum) -> rs.getInt(1), directEventId, consumerName).isEmpty();
	}
}
