package com.gomo.app.interest.presentation.response;

import java.util.UUID;

import lombok.Getter;

@Getter
public class CreateMajorInterestResponse {

	private UUID id;

	private CreateMajorInterestResponse(UUID id) {
		this.id = id;
	}

	public static CreateMajorInterestResponse of(UUID id) {
		return new CreateMajorInterestResponse(id);
	}
}
