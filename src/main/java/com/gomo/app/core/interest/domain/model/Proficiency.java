package com.gomo.app.core.interest.domain.model;

import com.gomo.app.common.arch.ValueObject;

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

	public Proficiency(Level level, Score score, int totalScore) {
		this.level = level;
		this.score = score;
		this.totalScore = totalScore;
	}

	public static Proficiency createDefault() {
		return new Proficiency(Level.createDefault(), Score.createDefault(), 0);
	}

	public int level() {
		return this.getLevel().getLevel();
	}

	public int score() {
		return this.getScore().getScore();
	}

	public Proficiency adjust(int deltaTotalScore, ProficiencyPolicies proficiencyPolicies) {
		return proficiencyPolicies.calculateProficiency(this.totalScore + deltaTotalScore);
	}
}
