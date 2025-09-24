package com.gomo.app.core.interest.presentation.response;

import java.util.List;

import lombok.Getter;

@Getter
public class ListInterestResponse {

	private List<ReadInterestResponse> interests;

	private ListInterestResponse(List<ReadInterestResponse> interests) {
		this.interests = interests;
	}

	public static ListInterestResponse of(List<ReadInterestResponse> interests) {
		return new ListInterestResponse(interests);
	}
}
