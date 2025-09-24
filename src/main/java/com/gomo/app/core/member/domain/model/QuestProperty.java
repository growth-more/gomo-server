package com.gomo.app.core.member.domain.model;

import com.gomo.app.common.ValueObject;

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

	public QuestProperty(DailyThreshold dailyThreshold, WeeklyThreshold weeklyThreshold, MonthlyThreshold monthlyThreshold) {
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
}
