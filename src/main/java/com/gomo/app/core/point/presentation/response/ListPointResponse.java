package com.gomo.app.core.point.presentation.response;

import java.util.List;
import java.util.UUID;

import com.gomo.app.core.point.application.port.dto.ListPointDto;

import lombok.Getter;

@Getter
public class ListPointResponse {

	private final List<ReadPointResponse> points;
	private final UUID lastElementId;

	private ListPointResponse(List<ReadPointResponse> points, UUID lastElementId) {
		this.points = points;
		this.lastElementId = lastElementId;
	}

	public static ListPointResponse from(ListPointDto dto) {
		return new ListPointResponse(
			dto.points().stream().map(ReadPointResponse::from).toList(),
			dto.lastElementId()
		);
	}
}
