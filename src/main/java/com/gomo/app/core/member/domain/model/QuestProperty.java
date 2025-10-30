package com.gomo.app.core.member.domain.model;

import com.gomo.app.common.arch.ValueObject;
import com.gomo.app.core.member.domain.exception.QuestPropertyConstraintViolationException;
import com.gomo.app.core.member.domain.exception.code.QuestPropertyErrorCode;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class QuestProperty {

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "threshold", column = @Column(name = "daily_quest_threshold"))
	})
	private DailyThreshold dailyThreshold;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "threshold", column = @Column(name = "weekly_quest_threshold"))
	})
	private WeeklyThreshold weeklyThreshold;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "threshold", column = @Column(name = "monthly_quest_threshold"))
	})
	private MonthlyThreshold monthlyThreshold;

	protected QuestProperty() {
	}

	private QuestProperty(DailyThreshold dailyThreshold, WeeklyThreshold weeklyThreshold, MonthlyThreshold monthlyThreshold) {
		this.dailyThreshold = dailyThreshold;
		this.weeklyThreshold = weeklyThreshold;
		this.monthlyThreshold = monthlyThreshold;
	}

	public static QuestProperty createDefault() {
		return new QuestProperty(DailyThreshold.of(5), WeeklyThreshold.of(5), MonthlyThreshold.of(5));
	}

	public static QuestProperty of(int dailyThreshold, int weeklyThreshold, int monthlyThreshold) {
		return new QuestProperty(DailyThreshold.of(dailyThreshold), WeeklyThreshold.of(weeklyThreshold), MonthlyThreshold.of(monthlyThreshold));
	}

	public static QuestProperty update(DailyThreshold dailyThreshold, WeeklyThreshold weeklyThreshold, MonthlyThreshold monthlyThreshold) {
		return new QuestProperty(dailyThreshold, weeklyThreshold, monthlyThreshold);
	}

	public int dailyThreshold() {
		return dailyThreshold.getThreshold();
	}

	public int weeklyThreshold() {
		return weeklyThreshold.getThreshold();
	}

	public int monthlyThreshold() {
		return monthlyThreshold.getThreshold();
	}

	@Getter
	@Embeddable
	@ValueObject
	static public class DailyThreshold {

		private static final int MINIMUM_THRESHOLD = 0;
		private static final int MAXIMUM_THRESHOLD = 15;
		private static final int DEFAULT_THRESHOLD = 5;

		private int threshold;

		protected DailyThreshold() {
		}

		private DailyThreshold(int threshold) {
			isValidSize(threshold);
			this.threshold = threshold;
		}

		public static DailyThreshold createDefault() {
			return new DailyThreshold(DEFAULT_THRESHOLD);
		}

		public static DailyThreshold of(int threshold) {
			return new DailyThreshold(threshold);
		}

		public DailyThreshold update(int threshold) {
			return DailyThreshold.of(threshold);
		}

		private void isValidSize(int threshold) {
			if (threshold < MINIMUM_THRESHOLD) {
				throw new QuestPropertyConstraintViolationException(QuestPropertyErrorCode.TOO_SMALL);
			}

			if (threshold > MAXIMUM_THRESHOLD) {
				throw new QuestPropertyConstraintViolationException(QuestPropertyErrorCode.TOO_LARGE);
			}
		}

		@Override
		public String toString() {
			return String.valueOf(this.threshold);
		}
	}

	@Getter
	@Embeddable
	@ValueObject
	static public class WeeklyThreshold {

		private static final int MINIMUM_THRESHOLD = 0;
		private static final int MAXIMUM_THRESHOLD = 15;
		private static final int DEFAULT_THRESHOLD = 3;

		private int threshold;

		protected WeeklyThreshold() {
		}

		private WeeklyThreshold(int threshold) {
			isValidSize(threshold);
			this.threshold = threshold;
		}

		public static WeeklyThreshold createDefault() {
			return new WeeklyThreshold(DEFAULT_THRESHOLD);
		}

		public static WeeklyThreshold of(int threshold) {
			return new WeeklyThreshold(threshold);
		}

		public WeeklyThreshold update(int threshold) {
			return WeeklyThreshold.of(threshold);
		}

		private void isValidSize(int threshold) {
			if (threshold < MINIMUM_THRESHOLD) {
				throw new QuestPropertyConstraintViolationException(QuestPropertyErrorCode.TOO_SMALL);
			}

			if (threshold > MAXIMUM_THRESHOLD) {
				throw new QuestPropertyConstraintViolationException(QuestPropertyErrorCode.TOO_LARGE);
			}
		}

		@Override
		public String toString() {
			return String.valueOf(this.threshold);
		}
	}

	@Getter
	@Embeddable
	@ValueObject
	static public class MonthlyThreshold {

		private static final int MINIMUM_THRESHOLD = 0;
		private static final int MAXIMUM_THRESHOLD = 15;
		private static final int DEFAULT_THRESHOLD = 3;

		private int threshold;

		protected MonthlyThreshold() {
		}

		private MonthlyThreshold(int threshold) {
			isValidSize(threshold);
			this.threshold = threshold;
		}

		public static MonthlyThreshold createDefault() {
			return new MonthlyThreshold(DEFAULT_THRESHOLD);
		}

		public static MonthlyThreshold of(int threshold) {
			return new MonthlyThreshold(threshold);
		}

		public MonthlyThreshold update(int threshold) {
			return MonthlyThreshold.of(threshold);
		}

		private void isValidSize(int threshold) {
			if (threshold < MINIMUM_THRESHOLD) {
				throw new QuestPropertyConstraintViolationException(QuestPropertyErrorCode.TOO_SMALL);
			}

			if (threshold > MAXIMUM_THRESHOLD) {
				throw new QuestPropertyConstraintViolationException(QuestPropertyErrorCode.TOO_LARGE);
			}
		}

		@Override
		public String toString() {
			return String.valueOf(this.threshold);
		}
	}
}
