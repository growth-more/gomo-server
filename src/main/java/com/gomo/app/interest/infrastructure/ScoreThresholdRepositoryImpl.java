package com.gomo.app.interest.infrastructure;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.gomo.app.interest.domain.model.ScoreThreshold;
import com.gomo.app.interest.domain.repository.ScoreThresholdRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ScoreThresholdRepositoryImpl implements ScoreThresholdRepository {

	private final JdbcTemplate jdbcTemplate;

	@Override
	public List<ScoreThreshold> findAll() {
		String sql = "select * from score_threshold";

		return jdbcTemplate.query(sql, (rs, rowNum) -> {
			ScoreThreshold.LevelRange levelRange = ScoreThreshold.LevelRange.of(rs.getInt("min_level"), rs.getInt("max_level"));
			return ScoreThreshold.of(levelRange, rs.getInt("threshold"));
		});
	}
}
