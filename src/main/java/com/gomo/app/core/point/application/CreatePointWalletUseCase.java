package com.gomo.app.core.point.application;

import java.util.UUID;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.core.point.application.port.CreatePointWalletPortIn;
import com.gomo.app.core.point.domain.model.PointWallet;
import com.gomo.app.core.point.domain.model.PointWalletId;
import com.gomo.app.core.point.domain.model.TransactorId;
import com.gomo.app.core.point.domain.repository.PointWalletRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
class CreatePointWalletUseCase implements CreatePointWalletPortIn {

	private final PointWalletRepository pointWalletRepository;

	@Override
	public UUID create(UUID transactorId) {
		PointWallet pointWallet = PointWallet.createDefault(PointWalletId.of(UUIDGenerator.generate()), TransactorId.of(transactorId));
		PointWallet savedPointWallet = pointWalletRepository.save(pointWallet);
		return savedPointWallet.id();
	}
}
