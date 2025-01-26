package com.gomo.app.interest.presentation.request;

import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.InterestName;
import com.gomo.app.interest.domain.model.RegistrantId;

import lombok.Getter;

@Getter
public class CreateInterestRequest {

	private String name;

	private CreateInterestRequest() {
	}

	public CreateInterestRequest(
		String name
	) {
		this.name = name;
	}

	public static CreateInterestRequest of(
		String name
	) {
		return new CreateInterestRequest(name);
	}

	public Interest toDomain(InterestId interestId, RegistrantId registrantId, String logoUrl) {
		return Interest.of(interestId, registrantId, InterestName.of(this.name), logoUrl);
	}
}
