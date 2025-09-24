package com.gomo.app.core.quest.domain.model;

import com.gomo.app.common.ValueObject;

import lombok.Getter;

@Getter
@ValueObject
public class QuestQuota {

	private int dailyQuota;
	private int weeklyQuota;
	private int monthlyQuota;

	protected QuestQuota() {
	}

	private QuestQuota(int dailyQuota, int weeklyQuota, int monthlyQuota) {
		this.dailyQuota = dailyQuota;
		this.weeklyQuota = weeklyQuota;
		this.monthlyQuota = monthlyQuota;
	}

	public static QuestQuota of(int dailyQuota, int weeklyQuota, int monthlyQuota) {
		return new QuestQuota(dailyQuota, weeklyQuota, monthlyQuota);
	}

	public boolean isExceeded(QuestType questType, int currentQuestCount) {
		return switch (questType) {
			case QuestType.DAILY -> this.dailyQuota <= currentQuestCount;
			case QuestType.WEEKLY -> this.weeklyQuota <= currentQuestCount;
			case QuestType.MONTHLY -> this.monthlyQuota <= currentQuestCount;
		};
	}

	public int getAvailableQuestSize(QuestType questType, int currentQuestCount) {
		return switch (questType) {
			case QuestType.DAILY -> this.dailyQuota - currentQuestCount;
			case QuestType.WEEKLY -> this.weeklyQuota - currentQuestCount;
			case QuestType.MONTHLY -> this.monthlyQuota - currentQuestCount;
		};
	}
}
