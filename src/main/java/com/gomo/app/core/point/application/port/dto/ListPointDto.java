package com.gomo.app.core.point.application.port.dto;

import java.util.List;
import java.util.UUID;

public record ListPointDto(List<PointDto> points, UUID lastElementId) {

	public static ListPointDto of(List<PointDto> points, UUID lastElementId) {
		return new ListPointDto(points, lastElementId);
	}
}
