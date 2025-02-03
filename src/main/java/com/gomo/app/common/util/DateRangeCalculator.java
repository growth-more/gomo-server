package com.gomo.app.common.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

public class DateRangeCalculator {

	public static LocalDateTime startOf(LocalDate now, String periodType) {
		return switch (periodType) {
			case "DAILY" -> now.atStartOfDay();
			case "WEEKLY" -> {
				LocalDate startOfWeek = now.with(DayOfWeek.MONDAY).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
				yield startOfWeek.atStartOfDay();
			}
			case "MONTHLY" -> {
				LocalDate startOfMonth = now.withDayOfMonth(1);
				yield startOfMonth.atStartOfDay();
			}
			default -> throw new IllegalArgumentException("Unknown quest type: " + periodType);
		};
	}

	public static LocalDateTime endOf(LocalDate now, String periodType) {
		return switch (periodType) {
			case "DAILY" -> now.atTime(23, 59, 59);
			case "WEEKLY" -> {
				LocalDate endOfWeek = now.with(DayOfWeek.SUNDAY).with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
				yield endOfWeek.atTime(23, 59, 59);
			}
			case "MONTHLY" -> {
				LocalDate endOfMonth = now.with(TemporalAdjusters.lastDayOfMonth());
				yield endOfMonth.atTime(23, 59, 59);
			}
			default -> throw new IllegalArgumentException("Unknown quest type: " + periodType);
		};
	}
}
