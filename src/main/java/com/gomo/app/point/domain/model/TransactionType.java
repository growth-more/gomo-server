package com.gomo.app.point.domain.model;

import lombok.Getter;

@Getter
public enum TransactionType {

	GAIN(1, "획득했습니다."),
	SPEND(-1, "사용했습니다.");

	private final int operationType;
	private final String description;

	TransactionType(int operationType, String description) {
		this.operationType = operationType;
		this.description = description;
	}
}
