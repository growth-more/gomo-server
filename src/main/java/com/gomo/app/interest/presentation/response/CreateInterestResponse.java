package com.gomo.app.interest.presentation.response;

import java.util.UUID;

import com.gomo.app.interest.domain.model.InterestId;

import lombok.Getter;

@Getter
public class CreateInterestResponse {

	private UUID id;

	private CreateInterestResponse(UUID id) {
		this.id = id;
	}

	public static CreateInterestResponse of(InterestId interestId) {
		return new CreateInterestResponse(interestId.getId());
	}
}
