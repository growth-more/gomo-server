package com.gomo.app.core.streak.domain.model;

import com.gomo.app.common.BaseAudit;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;

@Getter
@Entity
public class Achiever extends BaseAudit {

	@EmbeddedId
	private AchieverId id;
	private int currentStreakDays;
	private int longestStreakDays;

	protected Achiever() {
	}

	public Achiever(
		AchieverId id,
		int currentStreakDays,
		int longestStreakDays
	) {
		this.id = id;
		this.currentStreakDays = currentStreakDays;
		this.longestStreakDays = longestStreakDays;
	}

	public static Achiever of(AchieverId id) {
		return new Achiever(id, 0, 0);
	}

	public void adjustStreakDays(boolean isFilledPriorDay) {
		currentStreakDays = isFilledPriorDay ? currentStreakDays + 1 : 1;
		longestStreakDays = Math.max(longestStreakDays, currentStreakDays);
	}
}
