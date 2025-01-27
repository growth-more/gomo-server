package com.gomo.app.interest.integration;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gomo.app.common.IntegrationTestBase;
import com.gomo.app.interest.domain.model.Level;
import com.gomo.app.interest.domain.repository.ScoreThresholdRepository;
import com.gomo.app.interest.domain.service.ScoreThresholdService;

@DisplayName("[Domain integration]: 레벨 구간 별 임계점수 조회 테스트")
public class ScoreThresholdServiceTest extends IntegrationTestBase {

	private static final int MAXIMUM_LEVEL = 100;
	private static final int MAXIMUM_LEVEL_SCORE_THRESHOLD = 10000;

	@Autowired
	ScoreThresholdService sut;

	@Autowired
	ScoreThresholdRepository scoreThresholdRepository;

	@DisplayName("애플리케이션이 실행되면, 레벨 구간 별 점수 임계치가 캐시에 저장된다.")
	@Test
	void initialize_score_threshold_cache() {
		int[] cache = sut.findAll();
		int[] thresholds = findScoreThresholds(MAXIMUM_LEVEL);

		assertArrayEquals(cache, thresholds);
	}

	@DisplayName("현재 레벨에 해당하는 임계 점수를 조회한다.")
	@Test
	void find_score_threshold_by_level() {
		int scoreThreshold = sut.findScoreThreshold(Level.of(MAXIMUM_LEVEL));

		assertThat(scoreThreshold).isEqualTo(MAXIMUM_LEVEL_SCORE_THRESHOLD);
	}

	private int @NotNull [] findScoreThresholds(int length) {
		int[] thresholds = new int[length + 1];
		scoreThresholdRepository.findAll().forEach(scoreThreshold -> {
			for(int i = scoreThreshold.getMinLevel(); i<= scoreThreshold.getMaxLevel(); i++) {
				thresholds[i] = scoreThreshold.getThreshold();
			}
		});
		return thresholds;
	}
}
