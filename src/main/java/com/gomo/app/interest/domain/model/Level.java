package com.gomo.app.interest.domain.model;

import com.gomo.app.common.domain.ValueObject;
import com.gomo.app.common.exception.DomainErrorCode;
import com.gomo.app.common.exception.PolicyViolationException;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class Level {

	private static final int MAXIMUM_LEVEL = 100;

	private int level;

	protected Level() {
	}

	private Level(int level) {
		ensureNotNegative(level);
		ensureNotExceedMaximum(level);
		this.level = level;
	}

	public static Level createDefault() {
		return new Level(0);
	}

	public static Level of(int level) {
		return new Level(level);
	}

	public Level copy() {
		return new Level(this.level);
	}

	public Level increase(int increment) {
		ensurePositive(increment);

		int increasedLevel = this.level + increment;
		if(hasReachedMaximumLevel(increasedLevel)) {
			return new Level(MAXIMUM_LEVEL);
		}
		return new Level(increasedLevel);
	}

	private void ensurePositive(int increment) {
		if(increment <= 0) {
			throw new PolicyViolationException(DomainErrorCode.INVALID_PARAMETER, "Level increment must be positive.");
		}
	}

	private void ensureNotNegative(int level) {
		if(level < 0) {
			throw new PolicyViolationException(DomainErrorCode.INVALID_PARAMETER, "Level must be positive or zero.");
		}
	}

	private void ensureNotExceedMaximum(int level) {
		if(level > MAXIMUM_LEVEL) {
			throw new PolicyViolationException(DomainErrorCode.INVALID_PARAMETER, "Level cannot exceed the maximum level.");
		}
	}

	private boolean hasReachedMaximumLevel(int increasedLevel) {
		return increasedLevel >= MAXIMUM_LEVEL;
	}

	@Override
	public String toString() {
		return String.valueOf(this.level);
	}
}
