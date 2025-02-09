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
	private int scoreThreshold;
	private int totalScore;

	protected Proficiency() {
	}

	public Proficiency(
		Level level,
		Score score,
		int scoreThreshold,
		int totalScore
	) {
		this.level = level;
		this.score = score;
		this.scoreThreshold = scoreThreshold;
		this.totalScore = totalScore;
	}

	public static Proficiency createDefault() {
		return new Proficiency(Level.createDefault(), Score.createDefault(), 40, 0);
	}

	public static Proficiency of(Level level, Score score, int scoreThreshold, int totalScore) {
		return new Proficiency(level, score, scoreThreshold, totalScore);
	}

	public Proficiency adjust(int deltaTotalScore, int[] totalScoreForLevel, int[] scoreThresholdsPerLevel) {
		ensureTotalScoreNotNegative(deltaTotalScore);

		int adjustedTotalScore = this.totalScore + deltaTotalScore;
		return totalScoreToProficiency(adjustedTotalScore, totalScoreForLevel, scoreThresholdsPerLevel);
	}

	private void ensureTotalScoreNotNegative(int deltaTotalScore) {
		if(this.totalScore + deltaTotalScore < 0) {
			throw new ProficiencyAdjustFailureException(InterestErrorCode.INVALID_DELTA_TOTAL_SCORE);
		}
	}

	private Proficiency totalScoreToProficiency(int totalScore, int[] totalScoreForLevel, int[] scoreThresholdForLevel) {
		for(int level = 1; level < totalScoreForLevel.length; level++) {
			if(totalScoreForLevel[level] > totalScore) {
				int matchedLevel = level - 1;

				return new Proficiency(
					Level.of(matchedLevel),
					Score.of(totalScore - totalScoreForLevel[matchedLevel]),
					scoreThresholdForLevel[matchedLevel],
					totalScore
				);
			}
		}

		throw new ProficiencyAdjustFailureException(InterestErrorCode.TOTAL_SCORE_TOO_LARGE);
	}
}
