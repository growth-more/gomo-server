package com.gomo.app.interest.presentation.request;

import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.InterestName;
import com.gomo.app.interest.domain.model.RegistrantId;

import lombok.Getter;

@Getter
public class CreateInterestRequest {

	private String name;
	private String colorCode;

	private CreateInterestRequest() {
	}

	public CreateInterestRequest(String name, String colorCode) {
		this.name = name;
		this.colorCode = colorCode;
	}

	public static CreateInterestRequest of(String name, String colorCode) {
		return new CreateInterestRequest(name, colorCode);
	}

	public Interest toDomain(InterestId interestId, RegistrantId registrantId, String logoUrl) {
		return Interest.of(interestId, registrantId, InterestName.of(this.name), logoUrl, this.colorCode);
	}
}
