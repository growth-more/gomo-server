package com.gomo.app.core.interest.application.port.dto;

import java.util.UUID;

public record CreateInterestDto(UUID id) {

	public static CreateInterestDto of(UUID id) {
		return new CreateInterestDto(id);
	}
}
