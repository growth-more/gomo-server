package com.gomo.app.core.interest.unit.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gomo.app.core.interest.domain.model.Score;
import com.gomo.app.core.interest.exception.ScoreConstraintViolationException;
import com.gomo.app.core.interest.exception.code.ScoreErrorCode;

@DisplayName("[Domain unit]: 점수 생성, 증가, 조정, 레벨 증가량 확인 테스트")
public class ScoreTest {

	private static final int SCORE_THRESHOLD = 100;
	private static final int MAXIMUM_SCORE = 10000;

	@DisplayName("기본 점수는 0이다.")
	@Test
	void create_score() {
		Score score = Score.createDefault();

		assertThat(score.getScore()).isEqualTo(0);
	}

	@DisplayName("점수가 증가한다.")
	@Test
	void increase_score() {
		Score score = Score.createDefault();
		Score increasedScore = score.increase(10);

		assertThat(increasedScore.getScore()).isEqualTo(10);
	}

	@DisplayName("점수 증가량은 0일 수 없다.")
	@Test
	void increase_score_with_zero_increment() {
		Score score = Score.createDefault();

		assertThatThrownBy(() -> score.increase(0))
			.isInstanceOf(ScoreConstraintViolationException.class)
			.hasMessageContaining(ScoreErrorCode.NON_POSITIVE_INCREMENT.getMessage());
	}

	@DisplayName("점수 증가량은 음수일 수 없다.")
	@Test
	void increase_score_with_negative_increment() {
		Score score = Score.createDefault();

		assertThatThrownBy(() -> score.increase(-1))
			.isInstanceOf(ScoreConstraintViolationException.class)
			.hasMessageContaining(ScoreErrorCode.NON_POSITIVE_INCREMENT.getMessage());
	}

	@DisplayName("점수가 임계치에 도달하면 레벨 증가량은 1이다.")
	@Test
	void calculate_increased_level_by_score_reached_threshold() {
		Score score = new Score(SCORE_THRESHOLD);
		int increasedLevel = score.calculateIncreasedLevel(SCORE_THRESHOLD);

		assertThat(increasedLevel).isEqualTo(1);
	}

	@DisplayName("점수가 임계치 미만이라면 레벨 증가량은 0이다.")
	@Test
	void calculate_increased_level_by_less_than_threshold() {
		Score score = new Score(SCORE_THRESHOLD - 1);
		int increasedLevel = score.calculateIncreasedLevel(SCORE_THRESHOLD);

		assertThat(increasedLevel).isEqualTo(0);
	}

	@DisplayName("점수가 서비스 정책에 따른 최대값에 도달했다면, 레벨 증가량은 0이다.")
	@Test
	void calculate_increased_level_by_reached_max_score() {
		Score score = new Score(MAXIMUM_SCORE + 1);
		int increasedLevel = score.calculateIncreasedLevel(MAXIMUM_SCORE);

		assertThat(increasedLevel).isEqualTo(0);
	}

	@DisplayName("점수가 임계치 이상이라면 차액만 남는다.")
	@Test
	void trim_score_by_reached_threshold() {
		Score score = new Score(SCORE_THRESHOLD);
		Score trimmedScore = score.trimExcess(SCORE_THRESHOLD);

		assertThat(trimmedScore.getScore()).isEqualTo(0);
	}

	@DisplayName("점수가 임계치 미만이라면 변화없다.")
	@Test
	void not_trim_score_by_threshold() {
		Score score = new Score(SCORE_THRESHOLD - 1);
		Score trimmedScore = score.trimExcess(SCORE_THRESHOLD);

		assertThat(trimmedScore.getScore()).isEqualTo(SCORE_THRESHOLD - 1);
	}

	@DisplayName("점수가 서비스 정책에 따른 최대값에 도달했다면, 더 이상 증가하지 않는다.")
	@Test
	void trim_score_by_reached_max_score() {
		Score score = new Score(MAXIMUM_SCORE + 1);
		Score trimmedScore = score.trimExcess(MAXIMUM_SCORE);

		assertThat(trimmedScore.getScore()).isEqualTo(MAXIMUM_SCORE);
	}
}
