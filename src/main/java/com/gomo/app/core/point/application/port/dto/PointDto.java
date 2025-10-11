package com.gomo.app.core.point.application.port.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.gomo.app.core.point.domain.model.Point;
import com.gomo.app.core.point.domain.model.SourceType;
import com.gomo.app.core.point.domain.model.TransactionType;

public record PointDto(UUID id, SourceType sourceType, TransactionType transactionType, int amount, String description, LocalDateTime transactionDateTime) {

	public static PointDto from(Point point) {
		return new PointDto(point.getId(), point.getSourceType(), point.getTransactionType(), point.getAmount(), point.getDescription(), point.getTransactionDateTime());
	}
}
