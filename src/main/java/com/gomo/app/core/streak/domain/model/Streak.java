package com.gomo.app.core.streak.domain.model;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Getter
@Entity
public class Streak {

	@EmbeddedId
	private StreakId id;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "id", column = @Column(name = "achiever_id"))
	})
	private AchieverId achieverId;

	@Enumerated(value = EnumType.STRING)
	private StreakType streakType;
	private LocalDate filledDate;
	private int completedQuestCount;

	protected Streak() {
	}

	private Streak(StreakId id, AchieverId achieverId, StreakType streakType, LocalDate filledDate, int completedQuestCount) {
		this.id = id;
		this.achieverId = achieverId;
		this.streakType = streakType;
		this.filledDate = filledDate;
		this.completedQuestCount = completedQuestCount;
	}

	public static Streak of(StreakId id, AchieverId achieverId, StreakType streakType, LocalDate filledDate, int completedQuestCount) {
		return new Streak(id, achieverId, streakType, filledDate, completedQuestCount);
	}

	public UUID id() {
		return this.id.getId();
	}

	public void increaseCompletedQuestCount() {
		this.completedQuestCount++;
	}
}
