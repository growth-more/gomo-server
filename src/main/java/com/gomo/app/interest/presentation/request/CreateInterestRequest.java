package com.gomo.app.interest.presentation.request;

import org.springframework.web.multipart.MultipartFile;

import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.InterestName;
import com.gomo.app.interest.domain.model.Logo;
import com.gomo.app.interest.domain.model.RegistrantId;

import lombok.Getter;

@Getter
public class CreateInterestRequest {

	private String name;
	private String colorCode;
	private MultipartFile logo;

	private CreateInterestRequest() {
	}

	public CreateInterestRequest(String name, String colorCode, MultipartFile logo) {
		this.name = name;
		this.colorCode = colorCode;
		this.logo = logo;
	}

	public static CreateInterestRequest of(String name, String colorCode, MultipartFile logo) {
		return new CreateInterestRequest(name, colorCode, logo);
	}

	public Interest toDomain(InterestId interestId, RegistrantId registrantId, Logo logo) {
		return Interest.of(interestId, registrantId, InterestName.of(this.name), logo, this.colorCode);
	}
}
