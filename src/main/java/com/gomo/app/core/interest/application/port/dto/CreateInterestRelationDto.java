package com.gomo.app.core.interest.application.port.dto;

import java.util.UUID;

public record CreateInterestRelationDto(UUID id) {

	public static CreateInterestRelationDto of(UUID id) {
		return new CreateInterestRelationDto(id);
	}
}
