package com.gomo.app.member.presentation.request;

import com.gomo.app.member.domain.model.DailyThreshold;
import com.gomo.app.member.domain.model.MonthlyThreshold;
import com.gomo.app.member.domain.model.QuestProperty;
import com.gomo.app.member.domain.model.WeeklyThreshold;
import lombok.Getter;

@Getter
public class UpdateQuestPropertyRequest {

	private int dailyThreshold;
	private int weeklyThreshold;
	private int monthlyThreshold;

	private UpdateQuestPropertyRequest(
		int dailyThreshold,
		int weeklyThreshold,
		int monthlyThreshold
	) {
		this.dailyThreshold = dailyThreshold;
		this.weeklyThreshold = weeklyThreshold;
		this.monthlyThreshold = monthlyThreshold;
	}

	public static UpdateQuestPropertyRequest of(
		int dailyThreshold,
		int weeklyThreshold,
		int monthlyThreshold
	) {
		return new UpdateQuestPropertyRequest(dailyThreshold, weeklyThreshold, monthlyThreshold);
	}

	public QuestProperty toDomain(){
		return QuestProperty.update(DailyThreshold.of(dailyThreshold), WeeklyThreshold.of(weeklyThreshold), MonthlyThreshold.of(monthlyThreshold));
	}
}
