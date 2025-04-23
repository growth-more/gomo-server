package com.gomo.app.streak.domain.model;

import java.time.LocalDate;
import java.time.temporal.IsoFields;

import com.gomo.app.common.exception.DomainErrorCode;
import com.gomo.app.common.exception.PolicyViolationException;

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

	protected Streak() {}

	private Streak(
		StreakId id,
		AchieverId achieverId,
		StreakType streakType,
		LocalDate filledDate,
		int completedQuestCount
	) {
		this.id = id;
		this.achieverId = achieverId;
		this.streakType = streakType;
		this.filledDate = filledDate;
		this.completedQuestCount = completedQuestCount;
	}

	public static Streak of(
		StreakId id,
		AchieverId achieverId,
		StreakType streakType,
		LocalDate filledDate,
		int completedQuestCount
	) {
		return new Streak(id, achieverId, streakType, filledDate, completedQuestCount);
	}

	public void increaseCompletedQuestCount() {
		this.completedQuestCount++;
	}

	public int extractWeekOfYear() {
		ensureWeeklyType();
		return filledDate.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
	}

	private void ensureWeeklyType() {
		if(this.streakType != StreakType.WEEKLY) {
			throw new PolicyViolationException(DomainErrorCode.INVALID_STATE, "Only the WEEKLY type supports extracting the week from a date");
		}
	}
}
