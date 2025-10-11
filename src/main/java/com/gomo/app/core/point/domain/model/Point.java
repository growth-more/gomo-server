package com.gomo.app.core.point.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.gomo.app.common.jpa.BaseAudit;
import com.gomo.app.core.point.exception.PointConstraintViolationException;
import com.gomo.app.core.point.exception.code.PointErrorCode;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Point extends BaseAudit {

	@Id
	private UUID id;
	private UUID transactorId;

	@Enumerated(value = EnumType.STRING)
	private SourceType sourceType;

	@Enumerated(value = EnumType.STRING)
	private TransactionType transactionType;
	private int amount;
	private String description;
	private LocalDateTime transactionDateTime;

	protected Point() {
	}

	private Point(UUID id, UUID transactorId, SourceType sourceType, TransactionType transactionType, int amount, String description,
		LocalDateTime transactionDateTime) {
		ensureAmountNotNegative(amount);
		this.id = id;
		this.transactorId = transactorId;
		this.sourceType = sourceType;
		this.transactionType = transactionType;
		this.amount = amount;
		this.description = description;
		this.transactionDateTime = transactionDateTime;
	}

	public static Point of(UUID id, UUID transactorId, SourceType sourceType, TransactionType transactionType, int amount, String description,
		LocalDateTime transactionDateTime) {
		return new Point(id, transactorId, sourceType, transactionType, amount, description, transactionDateTime);
	}

	private void ensureAmountNotNegative(int amount) {
		if (amount < 0) {
			throw new PointConstraintViolationException(PointErrorCode.NEGATIVE);
		}
	}
}
