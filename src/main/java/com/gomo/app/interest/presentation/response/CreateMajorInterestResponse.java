package com.gomo.app.interest.presentation.response;

import java.util.UUID;

import com.gomo.app.interest.domain.model.MajorInterestId;

import lombok.Getter;

@Getter
public class CreateMajorInterestResponse {

	private UUID id;

	private CreateMajorInterestResponse(UUID id) {
		this.id = id;
	}

	public static CreateMajorInterestResponse of(MajorInterestId majorInterestId) {
		return new CreateMajorInterestResponse(majorInterestId.getId());
	}
}
