package com.gomo.app.core.interest.application.port.command;

import java.util.UUID;

public record UpdateInterestCommand(UUID registrantId, UUID interestId, String name, String colorCode) {

	public static UpdateInterestCommand of(UUID registrantId, UUID interestId, String name, String colorCode) {
		return new UpdateInterestCommand(registrantId, interestId, name, colorCode);
	}
}
