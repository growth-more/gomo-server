package com.gomo.app.interest.application.port.dto;

import java.util.List;

public record InterestNetworkDto(List<InterestDto> interestDtos, List<InterestRelationDto> relationDtos) {

	public static InterestNetworkDto of(List<InterestDto> interestDtos, List<InterestRelationDto> relationDtos) {
		return new InterestNetworkDto(interestDtos, relationDtos);
	}
}
