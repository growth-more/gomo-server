package com.gomo.app.core.member.domain.model;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gomo.app.core.member.exception.QuestPropertyConstraintViolationException;
import com.gomo.app.core.member.exception.code.QuestPropertyErrorCode;

@DisplayName("[Domain unit]: 일일 퀘스트 제한 테스트")
public class DailyThresholdTest {

	private static final int MINIMUM_THRESHOLD = 0;
	private static final int MAXIMUM_THRESHOLD = 15;
	private static final int DEFAULT_THRESHOLD = 5;

	@DisplayName("일일 퀘스트 제한을 생성한다.")
	@Test
	void create_daily_threshold() {
		DailyThreshold dailyThreshold = DailyThreshold.of(10);

		assertThat(dailyThreshold.getThreshold()).isEqualTo(10);
	}

	@DisplayName("기본 크키의 일일 퀘스트 제한을 생성한다.")
	@Test
	void create_daily_threshold_with_default_threshold() {
		DailyThreshold dailyThreshold = DailyThreshold.createDefault();

		assertThat(dailyThreshold.getThreshold()).isEqualTo(DEFAULT_THRESHOLD);
	}

	@DisplayName("최소 크기의 일일 퀘스트 제한을 생성한다.")
	@Test
	void create_daily_threshold_with_minimum_threshold() {
		DailyThreshold dailyThreshold = DailyThreshold.of(MINIMUM_THRESHOLD);

		assertThat(dailyThreshold.getThreshold()).isEqualTo(MINIMUM_THRESHOLD);
	}

	@DisplayName("최솟값 보다 작은 일일 퀘스트 제한은 생성할 수 없다.")
	@Test
	void create_daily_threshold_under_minimum_threshold() {
		assertThatThrownBy(() -> DailyThreshold.of(MINIMUM_THRESHOLD - 1))
			.isInstanceOf(QuestPropertyConstraintViolationException.class)
			.hasMessageContaining(QuestPropertyErrorCode.TOO_SMALL.getMessage());
	}

	@DisplayName("최대 크기의 일일 퀘스트 제한을 생성한다.")
	@Test
	void create_daily_threshold_with_maximum_threshold() {
		DailyThreshold dailyThreshold = DailyThreshold.of(MAXIMUM_THRESHOLD);

		assertThat(dailyThreshold.getThreshold()).isEqualTo(MAXIMUM_THRESHOLD);
	}

	@DisplayName("최대값 보다 큰 일일 퀘스트 제한은 생성할 수 없다.")
	@Test
	void create_daily_threshold_over_maximum_threshold() {
		assertThatThrownBy(() -> DailyThreshold.of(MAXIMUM_THRESHOLD + 1))
			.isInstanceOf(QuestPropertyConstraintViolationException.class)
			.hasMessageContaining(QuestPropertyErrorCode.TOO_LARGE.getMessage());
	}

	@DisplayName("일일 퀘스트 제한을 수정한다.")
	@Test
	void update_daily_threshold() {
		DailyThreshold dailyThreshold = DailyThreshold.of(10);
		DailyThreshold updatedDailyThreshold = dailyThreshold.update(11);

		assertThat(updatedDailyThreshold.getThreshold()).isEqualTo(11);
	}

	@DisplayName("최소 크기로 일일 퀘스트 제한을 수정한다.")
	@Test
	void update_daily_threshold_with_minimum_threshold() {
		DailyThreshold dailyThreshold = DailyThreshold.of(10);
		DailyThreshold updatedDailyThreshold = dailyThreshold.update(MINIMUM_THRESHOLD);

		assertThat(updatedDailyThreshold.getThreshold()).isEqualTo(MINIMUM_THRESHOLD);
	}

	@DisplayName("일일 퀘스트 제한은 최솟값 보다 작게 수정할 수 없다.")
	@Test
	void update_daily_threshold_under_minimum_threshold() {
		DailyThreshold dailyThreshold = DailyThreshold.of(10);

		assertThatThrownBy(() -> dailyThreshold.update(MINIMUM_THRESHOLD - 1))
			.isInstanceOf(QuestPropertyConstraintViolationException.class)
			.hasMessageContaining(QuestPropertyErrorCode.TOO_SMALL.getMessage());
	}

	@DisplayName("최대 크기로 일일 퀘스트 제한을 수정한다.")
	@Test
	void update_daily_threshold_with_maximum_threshold() {
		DailyThreshold dailyThreshold = DailyThreshold.of(10);
		DailyThreshold updatedDailyThreshold = dailyThreshold.update(MAXIMUM_THRESHOLD);

		assertThat(updatedDailyThreshold.getThreshold()).isEqualTo(MAXIMUM_THRESHOLD);
	}

	@DisplayName("일일 퀘스트 제한은 최대값 보다 크게 수정할 수 없다.")
	@Test
	void update_daily_threshold_over_maximum_threshold() {
		DailyThreshold dailyThreshold = DailyThreshold.of(10);

		assertThatThrownBy(() -> dailyThreshold.update(MAXIMUM_THRESHOLD + 1))
			.isInstanceOf(QuestPropertyConstraintViolationException.class)
			.hasMessageContaining(QuestPropertyErrorCode.TOO_LARGE.getMessage());
	}
}
