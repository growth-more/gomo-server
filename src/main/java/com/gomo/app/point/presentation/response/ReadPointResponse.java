package com.gomo.app.point.presentation.response;

import java.time.LocalDateTime;

import com.gomo.app.point.domain.model.Point;
import com.gomo.app.point.domain.model.SourceType;
import com.gomo.app.point.domain.model.TransactionType;

import lombok.Getter;

@Getter
public class ReadPointResponse {

	private SourceType sourceType;
	private TransactionType transactionType;
	private int amount;
	private String description;
	private LocalDateTime transactionDateTime;

	private ReadPointResponse(
		SourceType sourceType,
		TransactionType transactionType,
		int amount,
		String description,
		LocalDateTime transactionDateTime
	) {
		this.sourceType = sourceType;
		this.transactionType = transactionType;
		this.amount = amount;
		this.description = description;
		this.transactionDateTime = transactionDateTime;
	}

	public static ReadPointResponse of(Point point) {
		return new ReadPointResponse(
			point.getSourceType(),
			point.getTransactionType(),
			point.getAmount(),
			point.getDescription(),
			point.getTransactionDateTime()
		);
	}
}
