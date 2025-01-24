package com.gomo.app.quest.presentation.request;

import lombok.Getter;

@Getter
public class CompleteAssignQuestRequest {

	private String proof;

	private CompleteAssignQuestRequest(String proof) {
		this.proof = proof;
	}

	public static CompleteAssignQuestRequest of(String proof) {
		return new CompleteAssignQuestRequest(proof);
	}
}
