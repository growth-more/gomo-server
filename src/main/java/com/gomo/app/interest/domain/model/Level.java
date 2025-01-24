package com.gomo.app.interest.domain.model;

import com.gomo.app.common.domain.ValueObject;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class Level {

	private int level;

	protected Level() {
	}

	public Level(int level) {
		this.level = level;
	}

	public static Level createDefault() {
		return new Level(0);
	}

	public Level levelUp(int increment) {
		return new Level(this.level + increment);
	}

	private boolean exceedOneHundred() {
		return false;
	}
}
