package com.gomo.app.point.presentation.response;

import java.time.LocalDateTime;

import com.gomo.app.point.domain.model.PointType;
import com.gomo.app.point.domain.model.TransactionType;

import lombok.Getter;

@Getter
public class HistoryReadPointResponse {

	private PointType pointType;
	private TransactionType transactionType;
	private int points;
	private String description;
	private LocalDateTime transactionDateTime;

	private HistoryReadPointResponse(
		PointType pointType,
		TransactionType transactionType,
		int points,
		String description,
		LocalDateTime transactionDateTime
	) {
		this.pointType = pointType;
		this.transactionType = transactionType;
		this.points = points;
		this.description = description;
		this.transactionDateTime = transactionDateTime;
	}

	public static HistoryReadPointResponse of(
		PointType pointType,
		TransactionType transactionType,
		int points,
		String description,
		LocalDateTime transactionDateTime
	) {
		return new HistoryReadPointResponse(pointType, transactionType, points, description, transactionDateTime);
	}
}
