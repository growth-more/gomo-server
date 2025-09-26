package com.gomo.app.core.streak.domain.model;

import java.util.UUID;

import com.gomo.app.common.jpa.BaseAudit;

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

	public Achiever(AchieverId id, int currentStreakDays, int longestStreakDays) {
		this.id = id;
		this.currentStreakDays = currentStreakDays;
		this.longestStreakDays = longestStreakDays;
	}

	public static Achiever of(AchieverId id) {
		return new Achiever(id, 0, 0);
	}

	public UUID id() {
		return id.getId();
	}

	public void updateStreakDays(boolean isFilledPriorDay) {
		currentStreakDays = isFilledPriorDay ? currentStreakDays + 1 : 1;
		longestStreakDays = Math.max(longestStreakDays, currentStreakDays);
	}
}
