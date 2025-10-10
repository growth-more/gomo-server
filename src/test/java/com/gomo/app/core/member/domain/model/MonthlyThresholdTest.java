package com.gomo.app.core.member.domain.model;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gomo.app.core.member.exception.QuestPropertyConstraintViolationException;
import com.gomo.app.core.member.exception.code.QuestPropertyErrorCode;

@DisplayName("[Domain unit]: 월간 퀘스트 제한 테스트")
public class MonthlyThresholdTest {

	private static final int MINIMUM_THRESHOLD = 0;
	private static final int MAXIMUM_THRESHOLD = 15;
	private static final int DEFAULT_THRESHOLD = 3;

	@DisplayName("월간 퀘스트 제한을 생성한다.")
	@Test
	void create_monthly_threshold() {
		MonthlyThreshold monthlyThreshold = MonthlyThreshold.of(10);

		assertThat(monthlyThreshold.getThreshold()).isEqualTo(10);
	}

	@DisplayName("월간 크키의 일일 퀘스트 제한을 생성한다.")
	@Test
	void create_monthly_threshold_with_default_threshold() {
		MonthlyThreshold monthlyThreshold = MonthlyThreshold.createDefault();

		assertThat(monthlyThreshold.getThreshold()).isEqualTo(DEFAULT_THRESHOLD);
	}

	@DisplayName("월간 크기의 일일 퀘스트 제한을 생성한다.")
	@Test
	void create_monthly_threshold_with_minimum_threshold() {
		MonthlyThreshold monthlyThreshold = MonthlyThreshold.of(MINIMUM_THRESHOLD);

		assertThat(monthlyThreshold.getThreshold()).isEqualTo(MINIMUM_THRESHOLD);
	}

	@DisplayName("최솟값 보다 작은 월간 퀘스트 제한은 생성할 수 없다.")
	@Test
	void create_monthly_threshold_under_minimum_threshold() {
		assertThatThrownBy(() -> MonthlyThreshold.of(MINIMUM_THRESHOLD - 1))
			.isInstanceOf(QuestPropertyConstraintViolationException.class)
			.hasMessageContaining(QuestPropertyErrorCode.TOO_SMALL.getMessage());
	}

	@DisplayName("최대 크기의 월간 퀘스트 제한을 생성한다.")
	@Test
	void create_monthly_threshold_with_maximum_threshold() {
		MonthlyThreshold monthlyThreshold = MonthlyThreshold.of(MAXIMUM_THRESHOLD);

		assertThat(monthlyThreshold.getThreshold()).isEqualTo(MAXIMUM_THRESHOLD);
	}

	@DisplayName("최대값 보다 큰 월간 퀘스트 제한은 생성할 수 없다.")
	@Test
	void create_monthly_threshold_over_maximum_threshold() {
		assertThatThrownBy(() -> MonthlyThreshold.of(MAXIMUM_THRESHOLD + 1))
			.isInstanceOf(QuestPropertyConstraintViolationException.class)
			.hasMessageContaining(QuestPropertyErrorCode.TOO_LARGE.getMessage());
	}

	@DisplayName("월간 퀘스트 제한을 수정한다.")
	@Test
	void update_monthly_threshold() {
		MonthlyThreshold monthlyThreshold = MonthlyThreshold.of(10);
		MonthlyThreshold updatedMonthlyThreshold = monthlyThreshold.update(11);

		assertThat(updatedMonthlyThreshold.getThreshold()).isEqualTo(11);
	}

	@DisplayName("최소 크기로 월간 퀘스트 제한을 수정한다.")
	@Test
	void update_monthly_threshold_with_minimum_threshold() {
		MonthlyThreshold monthlyThreshold = MonthlyThreshold.of(10);
		MonthlyThreshold updatedMonthlyThreshold = monthlyThreshold.update(MINIMUM_THRESHOLD);

		assertThat(updatedMonthlyThreshold.getThreshold()).isEqualTo(MINIMUM_THRESHOLD);
	}

	@DisplayName("월간 퀘스트 제한은 최솟값 보다 작게 수정할 수 없다.")
	@Test
	void update_monthly_threshold_under_minimum_threshold() {
		MonthlyThreshold monthlyThreshold = MonthlyThreshold.of(10);

		assertThatThrownBy(() -> monthlyThreshold.update(MINIMUM_THRESHOLD - 1))
			.isInstanceOf(QuestPropertyConstraintViolationException.class)
			.hasMessageContaining(QuestPropertyErrorCode.TOO_SMALL.getMessage());
	}

	@DisplayName("최대 크기로 월간 퀘스트 제한을 수정한다.")
	@Test
	void update_monthly_threshold_with_maximum_threshold() {
		MonthlyThreshold monthlyThreshold = MonthlyThreshold.of(10);
		MonthlyThreshold updatedMonthlyThreshold = monthlyThreshold.update(MAXIMUM_THRESHOLD);

		assertThat(updatedMonthlyThreshold.getThreshold()).isEqualTo(MAXIMUM_THRESHOLD);
	}

	@DisplayName("월간 퀘스트 제한은 최대값 보다 크게 수정할 수 없다.")
	@Test
	void update_monthly_threshold_over_maximum_threshold() {
		MonthlyThreshold monthlyThreshold = MonthlyThreshold.of(10);

		assertThatThrownBy(() -> monthlyThreshold.update(MAXIMUM_THRESHOLD + 1))
			.isInstanceOf(QuestPropertyConstraintViolationException.class)
			.hasMessageContaining(QuestPropertyErrorCode.TOO_LARGE.getMessage());
	}
}
