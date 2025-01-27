package com.gomo.app.interest.integration;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gomo.app.common.IntegrationTestBase;
import com.gomo.app.interest.common.dataprovider.ScoreThresholdDataProvider;
import com.gomo.app.interest.domain.model.ScoreThreshold;
import com.gomo.app.interest.domain.repository.ScoreThresholdRepository;

@DisplayName("[Domain integration]: 레벨 구간 별 임계점수 DB 접근 테스트")
public class ScoreThresholdRepositoryTest extends IntegrationTestBase {

	@Autowired
	ScoreThresholdRepository sut;

	@DisplayName("서비스 정책에 따른 레벨 구간 별 임계 점수 목록을 조회한다.")
	@Test
	void find_all() {
		List<ScoreThreshold> actual = sut.findAll();
		List<ScoreThreshold> fixture = ScoreThresholdDataProvider.scoreThresholds();

		assertThat(actual)
			.hasSameSizeAs(fixture)
			.usingRecursiveFieldByFieldElementComparator()
			.containsExactlyElementsOf(fixture);
	}
}
