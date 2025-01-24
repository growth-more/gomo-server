package com.gomo.app.interest.infrastructure;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gomo.app.interest.domain.model.ScoreThreshold;
import com.gomo.app.interest.domain.repository.ScoreThresholdRepository;

@Repository
public class ScoreThresholdRepositoryImpl implements ScoreThresholdRepository {

	@Override
	public List<ScoreThreshold> findAll() {
		return null;
	}
}
