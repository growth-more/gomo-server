package com.gomo.app.core.interest.adapter.out.persistence;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.gomo.app.core.interest.domain.model.LevelThresholdPolicy;
import com.gomo.app.core.interest.domain.repository.LevelThresholdPolicyRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
class JDBCLevelThresholdPolicyRepository implements LevelThresholdPolicyRepository {

	private final JdbcTemplate jdbcTemplate;

	@Override
	public List<LevelThresholdPolicy> findAll() {
		String sql = "select * from level_threshold_policy";
		return jdbcTemplate.query(sql, (rs, rowNum) -> LevelThresholdPolicy.of(rs.getInt("level"), rs.getInt("threshold_score")));
	}
}
