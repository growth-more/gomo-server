package com.gomo.app.point.domain.model;

import java.time.LocalDateTime;

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
public class Point {

	@EmbeddedId
	private PointId id;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "id", column = @Column(name = "member_id"))
	})
	private TransactorId transactorId;

	@Enumerated(value = EnumType.STRING)
	private PointType pointType;

	@Enumerated(value = EnumType.STRING)
	private TransactionType transactionType;
	private int points;
	private String description;
	private LocalDateTime transactionDate;

	protected Point() {}

	private Point(
		PointId id,
		TransactorId transactorId,
		PointType pointType,
		TransactionType transactionType,
		int points,
		String description,
		LocalDateTime transactionDate
	) {
		this.id = id;
		this.transactorId = transactorId;
		this.pointType = pointType;
		this.transactionType = transactionType;
		this.points = points;
		this.description = description;
		this.transactionDate = transactionDate;
	}

	public static Point of(
		PointId id,
		TransactorId transactorId,
		PointType pointType,
		TransactionType transactionType,
		int points,
		String description,
		LocalDateTime transactionDate
	) {
		return new Point(id, transactorId, pointType, transactionType, points, description, transactionDate);
	}
}
