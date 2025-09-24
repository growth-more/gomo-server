package com.gomo.app.core.interest.application.port.dto;

import java.util.UUID;

public record RegistrantDto(UUID id, String subscriptionPlan) {

	public static RegistrantDto of(UUID id, String subscriptionPlan) {
		return new RegistrantDto(id, subscriptionPlan);
	}
}
