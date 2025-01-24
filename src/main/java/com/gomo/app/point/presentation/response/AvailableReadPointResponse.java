package com.gomo.app.point.presentation.response;

import lombok.Getter;

@Getter
public class AvailableReadPointResponse {

	private int availablePoints;

	private AvailableReadPointResponse(int availablePoints) {
		this.availablePoints = availablePoints;
	}

	public static AvailableReadPointResponse of(int availablePoints) {
		return new AvailableReadPointResponse(availablePoints);
	}
}
