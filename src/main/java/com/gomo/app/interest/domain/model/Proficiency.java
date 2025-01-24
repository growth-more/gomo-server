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
		int scoreToAdd,
		int scoreThreshold
	) {
		return null;
	}
}
