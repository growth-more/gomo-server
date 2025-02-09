package com.gomo.app.interest.common.dataprovider;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gomo.app.interest.domain.model.ScoreThreshold;

/**
 * 실제 데이터베이스에 존재하는 레벨 구간 별 임계점수 데이터를 제공한다.
 */
@Component
public class ScoreThresholdDataProvider {

	public static List<ScoreThreshold> scoreThresholds() {
		return List.of(
			ScoreThreshold.of(ScoreThreshold.LevelRange.of(0, 9), 40),
			ScoreThreshold.of(ScoreThreshold.LevelRange.of(10, 19), 60),
			ScoreThreshold.of(ScoreThreshold.LevelRange.of(20, 29), 80),
			ScoreThreshold.of(ScoreThreshold.LevelRange.of(30, 39), 100),
			ScoreThreshold.of(ScoreThreshold.LevelRange.of(40, 49), 120),
			ScoreThreshold.of(ScoreThreshold.LevelRange.of(50, 59), 140),
			ScoreThreshold.of(ScoreThreshold.LevelRange.of(60, 69), 160),
			ScoreThreshold.of(ScoreThreshold.LevelRange.of(70, 79), 180),
			ScoreThreshold.of(ScoreThreshold.LevelRange.of(80, 89), 200),
			ScoreThreshold.of(ScoreThreshold.LevelRange.of(90, 99), 220),
			ScoreThreshold.of(ScoreThreshold.LevelRange.of(100, 100), 10000)
		);
	}
}
