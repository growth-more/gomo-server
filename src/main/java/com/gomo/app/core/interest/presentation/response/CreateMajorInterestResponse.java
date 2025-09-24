package com.gomo.app.core.interest.presentation.response;

import java.util.UUID;

import lombok.Getter;

@Getter
public class CreateMajorInterestResponse {

	private UUID id;

	private CreateMajorInterestResponse(UUID id) {
		this.id = id;
	}

	public static CreateMajorInterestResponse of(UUID majorInterestId) {
		return new CreateMajorInterestResponse(majorInterestId);
	}
}
