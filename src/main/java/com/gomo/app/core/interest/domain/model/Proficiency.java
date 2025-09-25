package com.gomo.app.core.interest.domain.model;

import com.gomo.app.common.arch.ValueObject;
import com.gomo.app.core.interest.exception.ProficiencyAdjustFailureException;
import com.gomo.app.core.interest.exception.code.ProficiencyErrorCode;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class Proficiency {

	@Embedded
	private Level level;

	@Embedded
	private Score score;
	private int totalScore;

	protected Proficiency() {
	}

	public Proficiency(
		Level level,
		Score score,
		int totalScore
	) {
		this.level = level;
		this.score = score;
		this.totalScore = totalScore;
	}

	public static Proficiency createDefault() {
		return new Proficiency(Level.createDefault(), Score.createDefault(), 0);
	}

	public int level() {
		return this.getLevel().getLevel();
	}

	public int score() {
		return this.getScore().getScore();
	}

	public Proficiency adjust(int deltaTotalScore, int[] totalScoreForLevel, int[] scoreThresholdPerLevel) {
		ensureTotalScoreNotNegative(deltaTotalScore);

		int adjustedTotalScore = this.totalScore + deltaTotalScore;
		return totalScoreToProficiency(adjustedTotalScore, totalScoreForLevel, scoreThresholdPerLevel);
	}

	private void ensureTotalScoreNotNegative(int deltaTotalScore) {
		if(this.totalScore + deltaTotalScore < 0) {
			throw new ProficiencyAdjustFailureException(ProficiencyErrorCode.NEGATIVE_TOTAL_SCORE);
		}
	}

	private Proficiency totalScoreToProficiency(int totalScore, int[] totalScoreForLevel, int[] scoreThresholdPerLevel) {
		for(int level = 1; level < totalScoreForLevel.length; level++) {
			if(totalScoreForLevel[level] > totalScore) {
				int matchedLevel = level - 1;

				return new Proficiency(
					Level.of(matchedLevel, scoreThresholdPerLevel[matchedLevel]),
					Score.of(totalScore - totalScoreForLevel[matchedLevel]),
					totalScore
				);
			}
		}

		throw new ProficiencyAdjustFailureException(ProficiencyErrorCode.EXCEED_MAXIMUM_TOTAL_SCORE);
	}
}
