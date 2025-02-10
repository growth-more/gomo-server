package com.gomo.app.point.presentation.response;

import java.util.List;

import lombok.Getter;

@Getter
public class ListPointResponse {

	private List<ReadPointResponse> points;

	private ListPointResponse(List<ReadPointResponse> points) {
		this.points = points;
	}

	public static ListPointResponse of(List<ReadPointResponse> points) {
		return new ListPointResponse(points);
	}
}
