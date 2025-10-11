package com.gomo.app.core.point.application.usecase;

import java.util.UUID;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.core.point.application.port.CreatePointPortIn;
import com.gomo.app.core.point.domain.model.SourceType;
import com.gomo.app.core.point.domain.model.TransactionType;
import com.gomo.app.core.point.domain.service.PointService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
class CreatePointUseCase implements CreatePointPortIn {

	private final PointService pointService;

	@Override
	public void create(UUID transactorId, String sourceType, String transactionType, int amount) {
		pointService.create(transactorId, SourceType.valueOf(sourceType), TransactionType.valueOf(transactionType), amount);
	}
}
