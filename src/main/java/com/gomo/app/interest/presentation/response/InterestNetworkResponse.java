package com.gomo.app.interest.presentation.response;

import java.util.List;

import com.gomo.app.interest.application.port.dto.InterestNetworkDto;

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

	public static InterestNetworkResponse from(InterestNetworkDto interestNetworkDto) {
		List<ReadInterestResponse> interestResponses = interestNetworkDto.interestDtos().stream().map(ReadInterestResponse::from).toList();
		List<ReadInterestRelationResponse> relationResponses = interestNetworkDto.relationDtos().stream().map(ReadInterestRelationResponse::from).toList();
		return new InterestNetworkResponse(interestResponses, relationResponses);
	}
}
