package com.gomo.app.core.point.presentation.response;

import java.util.List;
import java.util.UUID;

import lombok.Getter;

@Getter
public class ListPointResponse {

	private List<ReadPointResponse> points;
	private UUID lastElementId;

	private ListPointResponse(List<ReadPointResponse> points, UUID lastElementId) {
		this.points = points;
		this.lastElementId = lastElementId;
	}

	public static ListPointResponse of(List<ReadPointResponse> points, UUID lastElementId) {
		return new ListPointResponse(points, lastElementId);
	}
}
