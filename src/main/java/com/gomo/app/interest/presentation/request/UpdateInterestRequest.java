package com.gomo.app.interest.presentation.request;

import java.util.UUID;

import com.gomo.app.interest.application.port.command.UpdateInterestCommand;

import lombok.Getter;

@Getter
public class UpdateInterestRequest {

	private String name;
	private String colorCode;

	private UpdateInterestRequest(String name, String colorCode) {
		this.name = name;
		this.colorCode = colorCode;
	}

	public static UpdateInterestRequest of(String name, String colorCode) {
		return new UpdateInterestRequest(name, colorCode);
	}

	public UpdateInterestCommand toCommand(UUID registrantId, UUID interestId) {
		return UpdateInterestCommand.of(registrantId, interestId, name, colorCode);
	}
}
