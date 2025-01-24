package com.gomo.app.interest.domain.model;

import com.gomo.app.common.domain.ValueObject;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class Score {

	private int score;

	protected Score() {
	}

	public Score(int score) {
		this.score = score;
	}

	public static Score createDefault() {
		return new Score(0);
	}

	public Score add(int increment) {
		return null;
	}

	public int calculateLevelIncrease() {
		return 0;
	}

	public Score adjust(int scoreThreshold) {
		return null;
	}

	private boolean exceedTenThousand() {
		return false;
	}
}
