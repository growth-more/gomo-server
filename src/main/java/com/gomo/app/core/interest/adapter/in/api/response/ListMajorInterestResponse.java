package com.gomo.app.core.interest.adapter.in.api.response;

import java.util.List;

import com.gomo.app.core.interest.application.port.dto.MajorInterestDto;

import lombok.Getter;

@Getter
public class ListMajorInterestResponse {

	private List<ReadMajorInterestResponse> majorInterests;

	private ListMajorInterestResponse(List<ReadMajorInterestResponse> majorInterests) {
		this.majorInterests = majorInterests;
	}

	public static ListMajorInterestResponse of(List<MajorInterestDto> majorInterestDtos) {
		return new ListMajorInterestResponse(majorInterestDtos.stream().map(ReadMajorInterestResponse::of).toList());
	}
}
