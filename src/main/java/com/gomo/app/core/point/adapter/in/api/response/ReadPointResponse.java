package com.gomo.app.core.point.adapter.in.api.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.gomo.app.core.point.application.port.dto.PointDto;
import com.gomo.app.core.point.domain.model.SourceType;
import com.gomo.app.core.point.domain.model.TransactionType;

import lombok.Getter;

@Getter
public class ReadPointResponse {

	private final UUID id;
	private final SourceType sourceType;
	private final TransactionType transactionType;
	private final int amount;
	private final String description;
	private final LocalDateTime transactionDateTime;

	private ReadPointResponse(UUID id, SourceType sourceType, TransactionType transactionType, int amount, String description, LocalDateTime transactionDateTime) {
		this.id = id;
		this.sourceType = sourceType;
		this.transactionType = transactionType;
		this.amount = amount;
		this.description = description;
		this.transactionDateTime = transactionDateTime;
	}

	public static ReadPointResponse from(PointDto dto) {
		return new ReadPointResponse(
			dto.id(),
			dto.sourceType(),
			dto.transactionType(),
			dto.amount(),
			dto.description(),
			dto.transactionDateTime()
		);
	}
}
