package com.gomo.app.interest.presentation.response;

import java.util.UUID;

import com.gomo.app.interest.application.port.dto.InterestRelationDto;

import lombok.Getter;

@Getter
public class ReadInterestRelationResponse {

	private UUID id;
	private UUID registrantId;
	private UUID parentInterestId;
	private UUID childInterestId;

	private ReadInterestRelationResponse(
		UUID id,
		UUID registrantId,
		UUID parentInterestId,
		UUID childInterestId
	) {
		this.id = id;
		this.registrantId = registrantId;
		this.parentInterestId = parentInterestId;
		this.childInterestId = childInterestId;
	}

	public static ReadInterestRelationResponse from(InterestRelationDto interestRelationDto) {
		return new ReadInterestRelationResponse(
			interestRelationDto.id(),
			interestRelationDto.registrantId(),
			interestRelationDto.parentInterestId(),
			interestRelationDto.childInterestId()
		);
	}
}
