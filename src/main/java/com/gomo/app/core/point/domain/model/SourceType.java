package com.gomo.app.core.point.domain.model;

import lombok.Getter;

@Getter
public enum SourceType {

	QUEST("퀘스트로부터 "),
	ATTENDANCE("출석으로부터 "),
	STORE("상점에서 "),
	EVENT("이벤트로부터 ");

	private final String description;

	SourceType(String description) {
		this.description = description;
	}
}
