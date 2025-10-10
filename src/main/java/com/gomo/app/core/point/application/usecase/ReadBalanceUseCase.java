package com.gomo.app.core.point.application.usecase;

import java.util.UUID;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.core.point.application.port.ReadBalancePortIn;
import com.gomo.app.core.point.domain.model.Balance;
import com.gomo.app.core.point.domain.model.TransactorId;
import com.gomo.app.core.point.domain.service.PointWalletService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
class ReadBalanceUseCase implements ReadBalancePortIn {

	private final PointWalletService pointWalletService;

	@Override
	public int find(UUID transactorId) {
		Balance balance = pointWalletService.findBalance(TransactorId.of(transactorId));
		return balance.getAmount();
	}
}
