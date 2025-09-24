package com.gomo.app.core.point.presentation.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.gomo.app.core.point.domain.model.Point;
import com.gomo.app.core.point.domain.model.SourceType;
import com.gomo.app.core.point.domain.model.TransactionType;

import lombok.Getter;

@Getter
public class ReadPointResponse {

	private UUID id;
	private SourceType sourceType;
	private TransactionType transactionType;
	private int amount;
	private String description;
	private LocalDateTime transactionDateTime;

	private ReadPointResponse(
		UUID id,
		SourceType sourceType,
		TransactionType transactionType,
		int amount,
		String description,
		LocalDateTime transactionDateTime
	) {
		this.id = id;
		this.sourceType = sourceType;
		this.transactionType = transactionType;
		this.amount = amount;
		this.description = description;
		this.transactionDateTime = transactionDateTime;
	}

	public static ReadPointResponse of(Point point) {
		return new ReadPointResponse(
			point.getId().getId(),
			point.getSourceType(),
			point.getTransactionType(),
			point.getAmount(),
			point.getDescription(),
			point.getTransactionDateTime()
		);
	}
}
