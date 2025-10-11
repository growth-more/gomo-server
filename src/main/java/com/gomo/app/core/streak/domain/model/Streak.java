package com.gomo.app.core.streak.domain.model;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Streak {

	@Id
	private UUID id;
	private UUID achieverId;

	@Enumerated(value = EnumType.STRING)
	private StreakType streakType;
	private LocalDate filledDate;
	private int completedQuestCount;

	protected Streak() {
	}

	private Streak(UUID id, UUID achieverId, StreakType streakType, LocalDate filledDate, int completedQuestCount) {
		this.id = id;
		this.achieverId = achieverId;
		this.streakType = streakType;
		this.filledDate = filledDate;
		this.completedQuestCount = completedQuestCount;
	}

	public static Streak of(UUID id, UUID achieverId, StreakType streakType, LocalDate filledDate, int completedQuestCount) {
		return new Streak(id, achieverId, streakType, filledDate, completedQuestCount);
	}

	public void increaseCompletedQuestCount() {
		this.completedQuestCount++;
	}
}
