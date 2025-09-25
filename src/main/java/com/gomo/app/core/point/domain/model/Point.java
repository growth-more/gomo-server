package com.gomo.app.core.point.domain.model;

import java.time.LocalDateTime;

import com.gomo.app.common.jpa.BaseAudit;
import com.gomo.app.core.point.exception.PointConstraintViolationException;
import com.gomo.app.core.point.exception.code.PointErrorCode;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Getter
@Entity
public class Point extends BaseAudit {

	@EmbeddedId
	private PointId id;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "id", column = @Column(name = "transactor_id"))
	})
	private TransactorId transactorId;

	@Enumerated(value = EnumType.STRING)
	private SourceType sourceType;

	@Enumerated(value = EnumType.STRING)
	private TransactionType transactionType;
	private int amount;
	private String description;
	private LocalDateTime transactionDateTime;

	protected Point() {
	}

	private Point(
		PointId id,
		TransactorId transactorId,
		SourceType sourceType,
		TransactionType transactionType,
		int amount,
		String description,
		LocalDateTime transactionDateTime
	) {
		ensureAmountNotNegative(amount);
		this.id = id;
		this.transactorId = transactorId;
		this.sourceType = sourceType;
		this.transactionType = transactionType;
		this.amount = amount;
		this.description = description;
		this.transactionDateTime = transactionDateTime;
	}

	public static Point of(
		PointId id,
		TransactorId transactorId,
		SourceType sourceType,
		TransactionType transactionType,
		int amount,
		String description,
		LocalDateTime transactionDateTime
	) {
		return new Point(id, transactorId, sourceType, transactionType, amount, description, transactionDateTime);
	}

	private void ensureAmountNotNegative(int amount) {
		if (amount < 0) {
			throw new PointConstraintViolationException(PointErrorCode.NEGATIVE);
		}
	}
}
