package com.gomo.app.core.point.application.usecase;

import java.util.UUID;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.core.point.application.port.CreatePointWalletPortIn;
import com.gomo.app.core.point.domain.model.PointWallet;
import com.gomo.app.core.point.domain.model.PointWalletId;
import com.gomo.app.core.point.domain.model.TransactorId;
import com.gomo.app.core.point.domain.repository.PointWalletRepository;
import com.gomo.app.support.logging.AuditLog;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
class CreatePointWalletUseCase implements CreatePointWalletPortIn {

	private final PointWalletRepository pointWalletRepository;

	// TODO [2025-10-10] jhl221123 : 이미 해당 transactor의 포인트 지갑이 존재한다면 예외가 발생해야한다.
	@AuditLog(action = "CREATE_POINT_WALLET")
	@Override
	public UUID create(UUID transactorId) {
		PointWallet pointWallet = PointWallet.createDefault(PointWalletId.of(UUIDGenerator.generate()), TransactorId.of(transactorId));
		PointWallet savedPointWallet = pointWalletRepository.save(pointWallet);
		return savedPointWallet.id();
	}
}
