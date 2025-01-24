package com.gomo.app.interest.presentation.response;

import java.util.List;

import lombok.Getter;

@Getter
public class ListMajorInterestResponse {

	private List<ReadMajorInterestResponse> majorInterests;

	private ListMajorInterestResponse(List<ReadMajorInterestResponse> majorInterests) {
		this.majorInterests = majorInterests;
	}

	public static ListMajorInterestResponse of(List<ReadMajorInterestResponse> majorInterests) {
		return new ListMajorInterestResponse(majorInterests);
	}
}
