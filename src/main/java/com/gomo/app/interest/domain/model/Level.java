package com.gomo.app.interest.domain.model;

import static com.gomo.app.common.exception.DomainErrorCode.*;

import com.gomo.app.common.domain.ValueObject;
import com.gomo.app.common.exception.PolicyViolationException;

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
			throw new PolicyViolationException(INVALID_PARAMETER, "Level must be positive or zero.");
		}
	}

	private void ensureNotExceedMaximum(int level) {
		if(level > MAXIMUM_LEVEL) {
			throw new PolicyViolationException(INVALID_PARAMETER, "Level cannot exceed the maximum level.");
		}
	}

	@Override
	public String toString() {
		return String.valueOf(this.level);
	}
}
