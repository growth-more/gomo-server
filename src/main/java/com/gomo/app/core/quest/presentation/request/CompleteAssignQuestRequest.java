package com.gomo.app.core.quest.presentation.request;

import java.util.UUID;

import com.gomo.app.core.quest.application.port.command.CompleteAssignQuestCommand;

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

	public CompleteAssignQuestCommand toCommand(UUID participantId, UUID assignQuestId) {
		return new CompleteAssignQuestCommand(participantId, assignQuestId, proof);
	}
}
