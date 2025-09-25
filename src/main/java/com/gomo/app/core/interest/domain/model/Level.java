package com.gomo.app.core.interest.domain.model;

import com.gomo.app.common.arch.ValueObject;
import com.gomo.app.core.interest.exception.LevelConstraintViolationException;
import com.gomo.app.core.interest.exception.code.LevelErrorCode;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class Level {

	private static final int MAXIMUM_LEVEL = 100;

	private int level;
	private int scoreThreshold;

	protected Level() {
	}

	private Level(int level, int scoreThreshold) {
		ensureNotNegative(level);
		ensureNotExceedMaximum(level);

		this.level = level;
		this.scoreThreshold = scoreThreshold;
	}

	public static Level createDefault() {
		return new Level(0, 40);
	}

	public static Level of(int level, int scoreThreshold) {
		return new Level(level, scoreThreshold);
	}

	public Level copy() {
		return new Level(this.level, this.scoreThreshold);
	}

	private void ensureNotNegative(int level) {
		if(level < 0) {
			throw new LevelConstraintViolationException(LevelErrorCode.NEGATIVE);
		}
	}

	private void ensureNotExceedMaximum(int level) {
		if(level > MAXIMUM_LEVEL) {
			throw new LevelConstraintViolationException(LevelErrorCode.EXCEED_MAXIMUM);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Level level = (Level)o;
		return this.level == level.level && scoreThreshold == level.scoreThreshold;
	}

	@Override
	public int hashCode() {
		int result = level;
		result = 31 * result + scoreThreshold;
		return result;
	}

	@Override
	public String toString() {
		return String.valueOf(this.level);
	}
}
