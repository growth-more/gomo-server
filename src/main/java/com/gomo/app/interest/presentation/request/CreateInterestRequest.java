package com.gomo.app.interest.presentation.request;

import org.springframework.web.multipart.MultipartFile;

import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.InterestName;
import com.gomo.app.interest.domain.model.RegistrantId;

import lombok.Getter;

@Getter
public class CreateInterestRequest {

	private String name;
	private MultipartFile logo;

	private CreateInterestRequest() {
	}

	public CreateInterestRequest(
		String name,
		MultipartFile logo
	) {
		this.name = name;
		this.logo = logo;
	}

	public static CreateInterestRequest of(
		String name,
		MultipartFile logo
	) {
		return new CreateInterestRequest(name, logo);
	}

	public Interest toDomain(InterestId interestId, RegistrantId registrantId, String logoUrl) {
		return Interest.of(interestId, registrantId, InterestName.of(this.name), logoUrl);
	}
}
