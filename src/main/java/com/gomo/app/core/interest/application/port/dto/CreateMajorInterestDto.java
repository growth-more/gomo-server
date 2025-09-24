package com.gomo.app.core.interest.application.port.dto;

import java.util.UUID;

public record CreateMajorInterestDto(UUID id) {

	public static CreateMajorInterestDto of(UUID id) {
		return new CreateMajorInterestDto(id);
	}
}
