package com.gomo.app.core.point.application.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.jetbrains.annotations.Nullable;
import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.logging.AuditLog;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.common.web.PageRequest;
import com.gomo.app.core.point.application.port.dto.ListPointDto;
import com.gomo.app.core.point.application.port.dto.PointDto;
import com.gomo.app.core.point.application.port.in.PointCreator;
import com.gomo.app.core.point.application.port.in.PointReader;
import com.gomo.app.core.point.domain.model.Point;
import com.gomo.app.core.point.domain.model.SourceType;
import com.gomo.app.core.point.domain.model.TransactionType;
import com.gomo.app.core.point.domain.repository.PointRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
class PointService implements PointCreator, PointReader {

	private final PointWalletService pointWalletService;
	private final PointRepository pointRepository;

	@Override
	@AuditLog(action = "POINT_CREATE")
	public void create(UUID transactorId, String sourceType, String transactionType, int amount) {
		SourceType source = SourceType.valueOf(sourceType);
		TransactionType transaction = TransactionType.valueOf(transactionType);
		Point point = Point.of(
			UUIDGenerator.generate(),
			transactorId,
			source,
			transaction,
			amount,
			source.getDescription() + transaction.getDescription(),
			LocalDateTime.now()
		);

		pointWalletService.adjustPointBalance(transactorId, transaction, amount);
		pointRepository.save(point);
	}

	@Override
	@Transactional(readOnly = true)
	public ListPointDto findAll(UUID transactorId, PageRequest pageRequest) {
		List<PointDto> points = pointRepository.findAllByTransactorId(
				transactorId.toString(),
				pageRequest.getLastElementTime(),
				pageRequest.getLastElementId(),
				pageRequest.getSize())
			.stream()
			.map(PointDto::from)
			.toList();
		return ListPointDto.of(points, getLastElementId(points));
	}

	@Nullable
	private UUID getLastElementId(List<PointDto> list) {
		if (list.isEmpty()) {
			return null;
		}
		return list.getLast().id();
	}
}
