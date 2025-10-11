package com.gomo.app.core.interest.domain.model;

import com.gomo.app.common.arch.ValueObject;
import com.gomo.app.core.interest.exception.ScoreConstraintViolationException;
import com.gomo.app.core.interest.exception.code.ScoreErrorCode;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@Embeddable
@ValueObject
public class Score {

	private static final int MAXIMUM_SCORE = 10000;

	private int score;

	protected Score() {
	}

	public Score(int score) {
		this.score = score;
	}

	public static Score createDefault() {
		return new Score(0);
	}

	public static Score of(int score) {
		return new Score(score);
	}

	public Score increase(int increment) {
		validatePositive(increment);
		return new Score(this.score + increment);
	}

	public int calculateIncreasedLevel(int scoreThreshold) {
		if (hasReachedMaxScore()) {
			return 0;
		}

		return this.score / scoreThreshold;
	}

	public Score trimExcess(int scoreThreshold) {
		if (hasReachedMaxScore()) {
			return new Score(MAXIMUM_SCORE);
		}

		if (this.score >= scoreThreshold) {
			return new Score(this.score - scoreThreshold);
		}
		return new Score(this.score);
	}

	private void validatePositive(int increment) {
		if (increment <= 0) {
			throw new ScoreConstraintViolationException(ScoreErrorCode.NON_POSITIVE_INCREMENT);
		}
	}

	private boolean hasReachedMaxScore() {
		return this.score >= MAXIMUM_SCORE;
	}

	@Override
	public String toString() {
		return String.valueOf(this.score);
	}
}
