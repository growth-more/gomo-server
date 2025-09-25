package com.gomo.app.core.interest.integration;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gomo.app.common.IntegrationTestBase;
import com.gomo.app.core.interest.domain.policy.InMemoryScoreThresholdPolicyProvider;

@DisplayName("[Domain integration]: 레벨 구간 별 임계점수 조회 테스트")
public class InMemoryScoreThresholdPolicyProviderTest extends IntegrationTestBase {

	@Autowired
	InMemoryScoreThresholdPolicyProvider sut;

	@DisplayName("숙련도 레벨별 임계 점수가 정상적으로 캐시된다.")
	@Test
	void cache_score_threshold_per_level() {
		int[] expected = new int[] {
			40, 40, 40, 40, 40, 40, 40, 40, 40, 40,
			60, 60, 60, 60, 60, 60, 60, 60, 60, 60,
			80, 80, 80, 80, 80, 80, 80, 80, 80, 80,
			100, 100, 100, 100, 100, 100, 100, 100, 100, 100,
			120, 120, 120, 120, 120, 120, 120, 120, 120, 120,
			140, 140, 140, 140, 140, 140, 140, 140, 140, 140,
			160, 160, 160, 160, 160, 160, 160, 160, 160, 160,
			180, 180, 180, 180, 180, 180, 180, 180, 180, 180,
			200, 200, 200, 200, 200, 200, 200, 200, 200, 200,
			220, 220, 220, 220, 220, 220, 220, 220, 220, 220, 10000
		};

		int[] scoreThresholdPerLevel = sut.getScoreThresholdPerLevel();

		assertThat(scoreThresholdPerLevel).isEqualTo(expected);
	}

	@DisplayName("숙련도 레벨별 도달에 필요한 총 점수가 정상적으로 캐시된다.")
	@Test
	void cache_total_score_per_level() {
		int[] expected = new int[] {
			0, 40, 80, 120, 160, 200, 240, 280, 320, 360, 400,
			460, 520, 580, 640, 700, 760, 820, 880, 940, 1000,
			1080, 1160, 1240, 1320, 1400, 1480, 1560, 1640, 1720, 1800,
			1900, 2000, 2100, 2200, 2300, 2400, 2500, 2600, 2700, 2800,
			2920, 3040, 3160, 3280, 3400, 3520, 3640, 3760, 3880, 4000,
			4140, 4280, 4420, 4560, 4700, 4840, 4980, 5120, 5260, 5400,
			5560, 5720, 5880, 6040, 6200, 6360, 6520, 6680, 6840, 7000,
			7180, 7360, 7540, 7720, 7900, 8080, 8260, 8440, 8620, 8800,
			9000, 9200, 9400, 9600, 9800, 10000, 10200, 10400, 10600, 10800,
			11020, 11240, 11460, 11680, 11900, 12120, 12340, 12560, 12780, 13000
		};

		int[] totalScoreForLevel = sut.getTotalScoreForLevel();

		assertThat(totalScoreForLevel).isEqualTo(expected);
	}
}
