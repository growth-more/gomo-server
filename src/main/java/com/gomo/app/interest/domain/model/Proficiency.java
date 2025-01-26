package com.gomo.app.interest.domain.model;

import com.gomo.app.common.domain.ValueObject;

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

	public Proficiency enhance(
		int scoreIncrement,
		int scoreThreshold
	) {
		int increasedTotalScore = this.totalScore + scoreIncrement;
		Score increasedScore = this.score.increase(scoreIncrement);
		Level increasedLevel = this.level.copy();

		int levelIncrement = increasedScore.calculateIncreasedLevel(scoreThreshold);
		if(possibleLevelUp(levelIncrement)) {
			increasedLevel = this.level.increase(levelIncrement);
			increasedScore = increasedScore.trimExcess(scoreThreshold);
		}

		return new Proficiency(increasedLevel, increasedScore, increasedTotalScore);
	}

	private boolean possibleLevelUp(int increasedLevel) {
		return increasedLevel > 0;
	}
}
