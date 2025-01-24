package com.gomo.app.interest.presentation.response;

import java.util.List;

import lombok.Getter;

@Getter
public class InterestNetworkResponse {

	private List<ReadInterestResponse> interests;
	private List<ReadInterestRelationResponse> relations;

	private InterestNetworkResponse(
		List<ReadInterestResponse> interests,
		List<ReadInterestRelationResponse> relations
	) {
		this.interests = interests;
		this.relations = relations;
	}

	public static InterestNetworkResponse of (
		List<ReadInterestResponse> interests,
		List<ReadInterestRelationResponse> relations
	) {
		return new InterestNetworkResponse(interests, relations);
	}
}
