package com.gomo.app.interest.presentation.request;

import lombok.Getter;

@Getter
public class UpdateInterestRequest {

	private String name;
	private String colorCode;

	private UpdateInterestRequest(String name, String colorCode) {
		this.name = name;
		this.colorCode = colorCode;
	}

	public static UpdateInterestRequest of(String name, String colorCode) {
		return new UpdateInterestRequest(name, colorCode);
	}
}
