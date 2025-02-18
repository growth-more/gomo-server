package com.gomo.app.member.domain.model;

import com.gomo.app.common.domain.ValueObject;

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

	public QuestProperty(
		DailyThreshold dailyThreshold,
		WeeklyThreshold weeklyThreshold,
		MonthlyThreshold monthlyThreshold
	) {
		this.dailyThreshold = dailyThreshold;
		this.weeklyThreshold = weeklyThreshold;
		this.monthlyThreshold = monthlyThreshold;
	}

	public static QuestProperty createDefault() {
		return new QuestProperty(DailyThreshold.of(0), WeeklyThreshold.of(0), MonthlyThreshold.of(0));
	}

	public static QuestProperty update(
		DailyThreshold dailyThreshold,
		WeeklyThreshold weeklyThreshold,
		MonthlyThreshold monthlyThreshold
	) {
		return new QuestProperty(dailyThreshold, weeklyThreshold, monthlyThreshold);
	}
}
