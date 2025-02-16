package com.gomo.app.interest.integration;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gomo.app.common.IntegrationTestBase;
import com.gomo.app.interest.domain.service.ScoreThresholdPolicyService;

@DisplayName("[Domain integration]: 레벨 구간 별 임계점수 조회 테스트")
public class ScoreThresholdPolicyServiceTest extends IntegrationTestBase {

	private static final int MAXIMUM_LEVEL = 100;

	@Autowired
	ScoreThresholdPolicyService sut;

	@Test
	void find_score_threshold_by_level() {
		int[] scoreThresholdsPerLevel = sut.getScoreThresholdPolicy();

		assertThat(scoreThresholdsPerLevel[MAXIMUM_LEVEL]).isEqualTo(10000);
	}

	@DisplayName("전체 레벨(0 ~ 100)의 총 점수 요구량을 조회한다.")
	@Test
	void find_total_score_for_level() {
		int[] totalScoreForLevel = sut.getTotalScoreForLevel();

		assertThat(totalScoreForLevel[MAXIMUM_LEVEL]).isEqualTo(13000);
	}
}
