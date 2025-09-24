package com.gomo.app.core.interest.infrastructure;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.gomo.app.core.interest.domain.model.ScoreThresholdPolicy;
import com.gomo.app.core.interest.domain.repository.ScoreThresholdPolicyRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ScoreThresholdPolicyRepositoryImpl implements ScoreThresholdPolicyRepository {

	private final JdbcTemplate jdbcTemplate;

	@Override
	public List<ScoreThresholdPolicy> findAll() {
		String sql = "select * from score_threshold_policy";

		return jdbcTemplate.query(sql, (rs, rowNum) -> ScoreThresholdPolicy.of(rs.getInt("level"), rs.getInt("threshold")));
	}
}
