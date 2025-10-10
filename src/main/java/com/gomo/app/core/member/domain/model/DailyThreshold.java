package com.gomo.app.core.member.domain.model;

import com.gomo.app.common.arch.ValueObject;
import com.gomo.app.core.member.exception.QuestPropertyConstraintViolationException;
import com.gomo.app.core.member.exception.code.QuestPropertyErrorCode;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class DailyThreshold {

	private static final int MINIMUM_THRESHOLD = 0;
	private static final int MAXIMUM_THRESHOLD = 15;
	private static final int DEFAULT_THRESHOLD = 5;

	private int threshold;

	protected DailyThreshold() {
	}

	private DailyThreshold(int threshold) {
		isValidSize(threshold);
		this.threshold = threshold;
	}

	public static DailyThreshold createDefault() {
		return new DailyThreshold(DEFAULT_THRESHOLD);
	}

	public static DailyThreshold of(int threshold) {
		return new DailyThreshold(threshold);
	}

	public DailyThreshold update(int threshold) {
		return DailyThreshold.of(threshold);
	}

	private void isValidSize(int threshold) {
		if(threshold < MINIMUM_THRESHOLD) {
			throw new QuestPropertyConstraintViolationException(QuestPropertyErrorCode.TOO_SMALL);
		}

		if(threshold > MAXIMUM_THRESHOLD) {
			throw new QuestPropertyConstraintViolationException(QuestPropertyErrorCode.TOO_LARGE);
		}
	}

	@Override
	public String toString() {
		return String.valueOf(this.threshold);
	}
}
