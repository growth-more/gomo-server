package com.gomo.app.interest.domain.model;

import com.gomo.app.common.domain.ValueObject;
import com.gomo.app.interest.exception.InterestErrorCode;
import com.gomo.app.interest.exception.ProficiencyAdjustFailureException;

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

	private Proficiency(
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

	public Proficiency adjust(int deltaTotalScore, int[] totalScoreForLevel, int[] scoreThresholdPolicy) {
		ensureTotalScoreNotNegative(deltaTotalScore);

		int adjustedTotalScore = this.totalScore + deltaTotalScore;
		return totalScoreToProficiency(adjustedTotalScore, totalScoreForLevel, scoreThresholdPolicy);
	}

	private void ensureTotalScoreNotNegative(int deltaTotalScore) {
		if(this.totalScore + deltaTotalScore < 0) {
			throw new ProficiencyAdjustFailureException(InterestErrorCode.TOTAL_SCORE_NEGATIVE);
		}
	}

	private Proficiency totalScoreToProficiency(int totalScore, int[] totalScoreForLevel, int[] scoreThresholdForLevel) {
		for(int level = 1; level < totalScoreForLevel.length; level++) {
			if(totalScoreForLevel[level] > totalScore) {
				int matchedLevel = level - 1;

				return new Proficiency(
					Level.of(matchedLevel, scoreThresholdForLevel[matchedLevel]),
					Score.of(totalScore - totalScoreForLevel[matchedLevel]),
					totalScore
				);
			}
		}

		throw new ProficiencyAdjustFailureException(InterestErrorCode.TOTAL_SCORE_TOO_LARGE);
	}
}
