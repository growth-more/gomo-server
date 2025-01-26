package com.gomo.app.interest.domain.model;

import com.gomo.app.common.domain.ValueObject;
import com.gomo.app.common.exception.DomainErrorCode;
import com.gomo.app.common.exception.PolicyViolationException;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;

/**
 * LevelRange 를 정적 내부 클래스로 사용한 이유
 * 1. 외부 클래스일 때, 객체의 용도를 명확하게 파악하기 힘들다. ScoreThreshold 와 함께 있을 때, 두 객체의 역할을 명확하게 파악할 수 있다.
 * 2. ScoreThreshold 내부 필드로 레벨 구간을 표현하는 것 대신 LevelRange 를 활용하면 '구간' 이라는 의미를 명확하게 표현할 수 있다.
 */
@Getter
@ValueObject
public class ScoreThreshold {

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "min", column = @Column(name = "min_level")),
		@AttributeOverride(name = "max", column = @Column(name = "max_level"))
	})
	private LevelRange levelRange;
	private int threshold;

	protected ScoreThreshold() {}

	private ScoreThreshold(LevelRange levelRange, int threshold) {
		this.levelRange = levelRange;
		this.threshold = threshold;
	}

	public static ScoreThreshold of(LevelRange levelRange, int threshold) {
		return new ScoreThreshold(levelRange, threshold);
	}

	public int getMinLevel() {
		return levelRange.getMin();
	}

	public int getMaxLevel() {
		return levelRange.getMax();
	}

	@Getter
	@Embeddable
	@ValueObject
	public static class LevelRange {

		private int min;

		private int max;

		protected LevelRange() {}

		private LevelRange(int min, int max) {
			if (min > max) {
				throw new PolicyViolationException(DomainErrorCode.INVALID_PARAMETER, "Min level cannot be greater than max level");
			}

			this.min = min;
			this.max = max;
		}

		public static LevelRange of(int min, int max) {
			return new LevelRange(min, max);
		}
	}
}
