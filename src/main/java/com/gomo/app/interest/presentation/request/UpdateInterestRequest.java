package com.gomo.app.interest.presentation.request;

import lombok.Getter;

@Getter
public class UpdateInterestRequest {

	private String name;

	private UpdateInterestRequest(String name) {
		this.name = name;
	}

	public static UpdateInterestRequest of(String name) {
		return new UpdateInterestRequest(name);
	}
}
