package com.gomo.app.core.point.domain.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.DomainService;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.core.point.domain.model.Point;
import com.gomo.app.core.point.domain.model.SourceType;
import com.gomo.app.core.point.domain.model.TransactionType;
import com.gomo.app.core.point.domain.repository.PointRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class PointService {

	private final PointWalletService pointWalletService;
	private final PointRepository pointRepository;

	@Transactional
	public UUID create(UUID transactorId, SourceType sourceType, TransactionType transactionType, int amount) {
		Point point = Point.of(
			UUIDGenerator.generate(),
			transactorId,
			sourceType,
			transactionType,
			amount,
			sourceType.getDescription() + transactionType.getDescription(),
			LocalDateTime.now()
		);

		pointWalletService.adjustPointBalance(transactorId, transactionType, amount);
		Point savedPoint = pointRepository.save(point);
		return savedPoint.getId();
	}
}
