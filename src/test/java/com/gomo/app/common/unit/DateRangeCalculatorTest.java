package com.gomo.app.common.unit;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gomo.app.common.util.DateRangeCalculator;

@DisplayName("[Domain unit]: 일 / 주 / 월의 시작 및 종료일 계산 테스트")
public class DateRangeCalculatorTest {

	private static final LocalDate PIVOT = LocalDate.of(2025, 2, 2);

	@DisplayName("금일의 시작 시점을 계산한다.")
	@Test
	void calculate_daily_start() {
		LocalDateTime startOfDay = DateRangeCalculator.startOf(PIVOT, "DAILY");

		assertThat(startOfDay).isEqualTo(LocalDateTime.of(2025, 2, 2, 0, 0, 0));
	}

	@DisplayName("금일의 끝 시점을 계산한다.")
	@Test
	void calculate_daily_end() {
		LocalDateTime endOfDay = DateRangeCalculator.endOf(PIVOT, "DAILY");

		assertThat(endOfDay).isEqualTo(LocalDateTime.of(2025, 2, 2, 23, 59, 59));
	}

	@DisplayName("금주의 시작 시점을 계산한다.")
	@Test
	void calculate_weekly_start() {
		LocalDateTime startOfWeek = DateRangeCalculator.startOf(PIVOT, "WEEKLY");

		assertThat(startOfWeek).isEqualTo(LocalDateTime.of(2025, 1, 27, 0, 0, 0));
	}

	@DisplayName("금주의 끝 시점을 계산한다.")
	@Test
	void calculate_weekly_end() {
		LocalDateTime endOfWeek = DateRangeCalculator.endOf(PIVOT, "WEEKLY");

		assertThat(endOfWeek).isEqualTo(LocalDateTime.of(2025, 2, 2, 23, 59, 59));
	}

	@DisplayName("금월의 시작 시점을 계산한다.")
	@Test
	void calculate_monthly_start() {
		LocalDateTime startOfMonth = DateRangeCalculator.startOf(PIVOT, "MONTHLY");

		assertThat(startOfMonth).isEqualTo(LocalDateTime.of(2025, 2, 1, 0, 0, 0));
	}

	@DisplayName("금월의 끝 시점을 계산한다.")
	@Test
	void calculate_monthly_end() {
		LocalDateTime endOfMonth = DateRangeCalculator.endOf(PIVOT, "MONTHLY");

		assertThat(endOfMonth).isEqualTo(LocalDateTime.of(2025, 2, 28, 23, 59, 59));
	}
}
